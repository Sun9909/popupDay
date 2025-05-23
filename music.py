import discord
from discord.ext import commands
from discord import FFmpegOpusAudio
import asyncio
import yt_dlp 
from datetime import datetime
import shutil
import subprocess
from zoneinfo import ZoneInfo

# ─── 전역 상태 ─────────────────────────────────────────────
queues = {}
guild_current = {}
play_channel = {}
loop_mode = {}
autoplay_mode = {}
idle_timers = {}
embed_tasks = {}
IDLE_TIMEOUT = 300

# ─── yt_dlp 설정 ───────────────────────────────────────────
YTDL_OPTS = {
    'format': 'bestaudio/best',
    'noplaylist': True,
    'quiet': True,
    'default_search': 'ytsearch',
    'skip_download': True,
    'cookiefile': 'cookies.txt',
    'headers': {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36'
}
}
ytdl = yt_dlp.YoutubeDL(YTDL_OPTS)

# ─── FFMPEG 옵션 ───────────────────────────────────────────
FFMPEG_RECONNECT = (
    '-reconnect 1 '
    '-reconnect_streamed 1 '
    '-reconnect_delay_max 5'
)

# ─── 유틸리티 ─────────────────────────────────────────────────
async def auto_disconnect_if_empty(gid, vc, delay=300):
    await asyncio.sleep(delay)
    if not vc.is_connected():
        return

    print(f"[DEBUG] VC 인원 수: {len(vc.channel.members)} | 재생 중: {vc.is_playing()}")

    # 실제 유저만 필터링 (봇 제외)
    non_bot_members = [m for m in vc.channel.members if not m.bot]

    if len(non_bot_members) == 0 and not vc.is_playing():  # 유저 없음 + 재생 중 아님
        try:
            await vc.disconnect()
            print(f"[INFO] {delay}초 경과, 사용자 없음 → 자동 퇴장됨 (GID: {gid})", flush=True)
        except Exception as e:
            print(f"[ERROR] 자동 퇴장 실패: {e}", flush=True)


async def update_playing_embed(vc, gid, message):
    try:
        while True:
            cur = guild_current.get(gid, {})
            cur['offset'] = cur.get('offset', 0) + 5
            embed = build_now_embed(vc, gid)
            msg = cur.get('message')
            if msg:
                await msg.edit(embed=embed)
            await asyncio.sleep(5)
    except asyncio.CancelledError:
        print(f"[INFO] embed update task for GID {gid} cancelled.")

async def clear_bot_messages(channel: discord.TextChannel, limit=50):
    async for msg in channel.history(limit=limit):
        if msg.author == channel.guild.me:
            try:
                await msg.delete()
            except Exception as e:
                print(f"[ERROR] 메시지 삭제 실패: {e}")
# ─── 함수들 ─────────────────────────────────────────────────
async def ytdl_search(query: str):
    loop = asyncio.get_event_loop()
    info = await loop.run_in_executor(None, lambda: ytdl.extract_info(f'ytsearch5:{query}', download=False))

    if not info.get('entries'):
        return None, "검색 실패", 0, None

    for entry in info['entries']:
        # 연령제한/비공개/라이브 차단 + 기본적인 로그인 필요 방지
        if entry.get('age_limit', 0) == 0 and not entry.get('is_private') and entry.get('live_status') != 'is_live':
            return (
                entry.get('url') or entry.get('webpage_url'),
                entry.get('title', query),
                entry.get('duration', 0),
                entry.get('thumbnail')
            )

    return None, "로그인 필요한 영상만 검색됨", 0, None


async def cancel_idle_timer(gid):
    if task := idle_timers.pop(gid, None):
        task.cancel()

async def idle_disconnect(gid, vc):
    await asyncio.sleep(IDLE_TIMEOUT)
    if not queues.get(gid) and vc.is_connected():
        await vc.disconnect()

def build_now_embed(vc, gid):
    cur = guild_current.get(gid, {})
    embed = discord.Embed(title='🎶 재생 중', description=cur.get('title', ''), color=0xFFAA00)
    length, offset = cur.get('length', 0), cur.get('offset', 0)
    if length:
        pos = int(10 * offset / length)
        bar = '─' * pos + '🔘' + '─' * (10 - pos)
        t1, t2 = f"{offset//60}:{offset%60:02d}", f"{length//60}:{length%60:02d}"
        embed.add_field(name=f"{t1} {bar} {t2}", value='​', inline=False)
    if cur.get('thumbnail'):
        embed.set_thumbnail(url=cur['thumbnail'])
    start_time = cur.get('start_time') or datetime.now(ZoneInfo("Asia/Seoul"))
    embed.set_footer(text=f"요청: {cur.get('requester', '?')} | {start_time.strftime('%p %I:%M')}")
    return embed


async def play_next(gid, vc):
    if not vc.is_connected():
        print(f"[DEBUG] play_next 중단됨: VC 연결이 끊김 (GID: {gid})")
        return

    await cancel_idle_timer(gid)  # ← idle 중복 방지

    def after_callback(gid, vc, source):
        async def wrapper(error):
            if error:
                print(f"[ERROR] Playback error: {error}")
            try:
                if hasattr(source, "cleanup") and callable(source.cleanup):
                    source.cleanup()
            except Exception as e:
                print(f"[ERROR] source cleanup failed: {e}")
            await play_next(gid, vc)
        return lambda e: asyncio.run_coroutine_threadsafe(wrapper(e), vc.loop)

    print(f"[DEBUG] ▶ play_next() 호출됨 | GID: {gid} | VC 연결됨={vc.is_connected()} 재생중={vc.is_playing()}", flush=True)
    q = queues.get(gid, [])
    print(f"[DEBUG] 현재 큐 상태: {q}", flush=True)

    if not q:
        if autoplay_mode.get(gid):
            last = guild_current.get(gid)
            if last:
                print(f"[DEBUG] 자동재생 모드 활성화 → 검색 시작: {last['title']} cover", flush=True)
                url, title, length, thumb = await ytdl_search(last['title'] + ' cover')
                if url:
                    print(f"[DEBUG] 자동재생 검색 결과: {title}, {url}", flush=True)
                    q.append({
                        'url': url,
                        'title': title,
                        'offset': 0,
                        'length': length,
                        'requester': '자동',
                        'thumbnail': thumb
                    })
                else:
                    print("[ERROR] 자동재생 검색 실패 → URL 없음", flush=True)
        else:
            print("[DEBUG] 큐 비었고 자동재생 OFF → idle_disconnect 등록", flush=True)
            await cancel_idle_timer(gid)
            idle_timers[gid] = asyncio.create_task(idle_disconnect(gid, vc))

        if not q:
            print("[DEBUG] 자동재생 실패 또는 큐 없음 → return", flush=True)

            guild_current.pop(gid, None)
            queues.pop(gid, None)
            play_channel.pop(gid, None)
            loop_mode.pop(gid, None)
            autoplay_mode.pop(gid, None)
            idle_timers.pop(gid, None)

            return

    item = q.pop(0)
    print(f"[DEBUG] ▶ 큐에서 재생 항목 꺼냄: {item}", flush=True)

    if loop_mode.get(gid):
        queues[gid].append(item)
        print(f"[DEBUG] 반복 모드 → 항목을 큐에 다시 추가: {item['title']}", flush=True)

    url, title, offset, length = item['url'], item['title'], item['offset'], item['length']
    guild_current[gid] = {
        'title': title,
        'offset': offset,
        'length': length,
        'requester': item.get('requester', '?'),
        'thumbnail': item.get('thumbnail')
    }

    ffmpeg_path = "/usr/bin/ffmpeg"
    before = FFMPEG_RECONNECT if offset == 0 else f"-ss {offset} {FFMPEG_RECONNECT}"
    channel_bitrate = getattr(vc.channel, 'bitrate', 64000)
    bitrate = max(64000, min(channel_bitrate, 384000)) // 1000
    opts = f"-vn -c:a libopus -b:a {bitrate}k"

    print(f"[DEBUG] FFMPEG 설정:\n  - path: {ffmpeg_path}\n  - before: {before}\n  - options: {opts}", flush=True)
    print(f"[DEBUG] 재생 대상:\n  - title: {title}\n  - url: {url}\n  - offset: {offset}\n  - length: {length}", flush=True)

    try:
        result = subprocess.run([ffmpeg_path, "-version"], check=True, capture_output=True, text=True)
        print(f"[DEBUG] ffmpeg 정상 작동 확인: {result.stdout.splitlines()[0]}", flush=True)
    except Exception as e:
        print(f"[ERROR] ffmpeg 실행 실패: {e}", flush=True)
        return

    try:
        print("[DEBUG] FFmpegOpusAudio.from_probe 실행 시작", flush=True)
        source = await FFmpegOpusAudio.from_probe(
            url,
            executable=ffmpeg_path,
            before_options=before,
            options=opts
        )
        print("[DEBUG] FFmpegOpusAudio 생성 성공", flush=True)
        vc.play(source, after=after_callback(gid, vc, source))
    except Exception as e:
        print(f"[ERROR] FFmpegOpusAudio 생성 실패: {e}", flush=True)
        print("[DEBUG] Fallback: FFmpegPCMAudio 시도 중...", flush=True)
        
        try:
            source = discord.FFmpegPCMAudio(
                url,
                executable=ffmpeg_path,
                before_options=before,
                options="-f s16le -ar 48000 -ac 2"
            )
            print("[DEBUG] FFmpegPCMAudio 생성 성공", flush=True)
        except Exception as e2:
            # ❗ 여기서만 embed 보내기
            if channel := play_channel.get(gid):
                await channel.send(embed=discord.Embed(
                    title="❌ 재생 실패",
                    description=f"Opus/PCM 오디오 생성 실패\n{e2}",
                    color=0xFF0000
                ))
            return

    print(f"[DEBUG] ▶ 재생 시작됨: {title}", flush=True)
    #await asyncio.sleep(3)
    print(f"[DEBUG] 3초 후 vc.is_playing(): {vc.is_playing()}", flush=True)
    if not vc.is_playing():
        print("[ERROR] 🎵 재생 실패 감지 - 0초에서 멈춘 것으로 보임", flush=True)

    if channel := play_channel.get(gid):
        # ✅ 봇이 보낸 이전 메시지 전체 삭제 (최근 50개)
        async for msg in channel.history(limit=50):
            if msg.author == channel.guild.me:
                try:
                    await msg.delete()
                except Exception as e:
                    print(f"[ERROR] 메시지 삭제 실패: {e}")
        try:
            embed = build_now_embed(vc, gid)

            # ✅ embed 전송 및 저장
            msg = await channel.send(embed=embed, view=MusicControls(gid))
            guild_current[gid]['message'] = msg

            # ✅ 재생 시작 시각 저장 (한 번만)
            if 'start_time' not in guild_current[gid]:
                guild_current[gid]['start_time'] = datetime.now(ZoneInfo("Asia/Seoul"))

        except Exception as e:
            print(f"[ERROR] embed 전송 실패: {e}", flush=True)

    # ✅ embed 갱신 작업 등록
    if task := embed_tasks.get(gid):
        task.cancel()
    task = asyncio.create_task(update_playing_embed(vc, gid, guild_current[gid]['message']))
    embed_tasks[gid] = task
    return
    
# ─── UI 버튼 컨트롤 ────────────────────────────────────────
class MusicControls(discord.ui.View):
    def __init__(self, gid):
        super().__init__(timeout=None)
        self.gid = gid

    @discord.ui.button(label="⏹ 정지", style=discord.ButtonStyle.danger, row=0)
    async def stop(self, inter, _):
        if vc := inter.guild.voice_client:
            queues[self.gid].clear()
            vc.stop()
            if task := embed_tasks.pop(self.gid, None):
                task.cancel()
        await inter.response.defer()

    @discord.ui.button(label="⏸ 일시정지", style=discord.ButtonStyle.secondary, row=0)
    async def pause(self, inter, _):
        if vc := inter.guild.voice_client:
            if vc.is_playing():
                vc.pause()
        await inter.response.defer()

    @discord.ui.button(label="▶ 다시재생", style=discord.ButtonStyle.success, row=0)
    async def resume(self, inter, _):
        if vc := inter.guild.voice_client:
            if vc.is_paused():
                vc.resume()
                gid = self.gid
                if task := embed_tasks.get(gid):
                    task.cancel()
                task = asyncio.create_task(update_playing_embed(vc, gid, guild_current[gid].get('message')))
                embed_tasks[gid] = task
        await inter.response.defer()

    @discord.ui.button(label="⏭ 스킵", style=discord.ButtonStyle.primary, row=1)
    async def skip(self, inter, _):
        if vc := inter.guild.voice_client:
            vc.stop()
        await inter.response.defer()

    # @discord.ui.button(label="⏪ 10초 뒤로", style=discord.ButtonStyle.secondary, row=2)
    # async def rewind_10(self, inter, _):
    #     cur = guild_current.get(self.gid, {})
    #     cur['offset'] = max(0, cur.get('offset', 0) - 10)
    #     vc = inter.guild.voice_client
    #     vc.stop()
    #     await inter.response.defer()
    #     await inter.followup.send("⏪ 10초 뒤로 이동", ephemeral=True)
    #     await play_next(self.gid, vc)

    # @discord.ui.button(label="⏩ 10초 앞으로", style=discord.ButtonStyle.secondary, row=2)
    # async def forward_10(self, inter, _):
    #     cur = guild_current.get(self.gid, {})
    #     cur['offset'] = cur.get('offset', 0) + 10
    #     vc = inter.guild.voice_client
    #     vc.stop()
    #     await inter.response.defer()
    #     await inter.followup.send("⏩ 10초 앞으로 이동", ephemeral=True)
    #     await play_next(self.gid, vc)

    # @discord.ui.button(label="🕹 구간 이동", style=discord.ButtonStyle.secondary, row=2)
    # async def seek(self, inter, _):
    #     await inter.response.send_modal(SeekModal(self.gid))

    @discord.ui.button(label="📑 대기열", style=discord.ButtonStyle.secondary, row=1)
    async def queue(self, inter, _):
        msg = await inter.response.send_message(
            embed=build_queue_embed(self.gid),
            view=QueueView(self.gid),
            ephemeral=False
        )
        # 메세지 ID 저장해서 나중에 edit 할 수 있도록
        if self.gid not in guild_current:
            guild_current[self.gid] = {}
        guild_current[self.gid]['queue_message'] = await inter.original_response()


# ─── 메인 Setup 함수 ────────────────────────────────────────
def setup(bot: commands.Bot):
    @bot.command(name="재생")
    async def play(ctx, *, query: str):
        if not ctx.author.voice:
            return await ctx.send("먼저 음성 채널에 입장해주세요.")
        vc = ctx.voice_client or await ctx.author.voice.channel.connect()
        gid = ctx.guild.id
        play_channel[gid] = ctx.channel
        url, title, length, thumb = await ytdl_search(query)
        if not url:
            return await ctx.send("검색 결과를 찾을 수 없습니다.")
        queues.setdefault(gid, []).append({
            'url': url,
            'title': title,
            'offset': 0,
            'length': length,
            'requester': ctx.author.display_name,
            'thumbnail' : thumb
            })
        
        msg = await ctx.send("✅ 재생 요청됨")
        guild_current.setdefault(gid, {})['request_message'] = msg

        if not vc.is_playing():
            await play_next(gid, vc)

    @bot.command(name="정지")
    async def stop(ctx):
        if vc := ctx.voice_client:
            queues[ctx.guild.id] = []
            vc.stop()
            await ctx.send('⏹ 재생 정지 및 대기열 초기화')

    @bot.command(name="일시정지")
    async def pause(ctx):
        if vc := ctx.voice_client:
            if vc.is_playing():
                vc.pause()
                await ctx.send('⏸ 일시정지되었습니다.')

    @bot.command(name="다시재생")
    async def resume(ctx):
        if vc := ctx.voice_client:
            if vc.is_paused():
                vc.resume()
                await ctx.send('▶️ 재생이 재개되었습니다.')

    @bot.command(name="자동재생")
    async def autoplay(ctx):
        gid = ctx.guild.id
        autoplay_mode[gid] = not autoplay_mode.get(gid, False)
        await ctx.send(f"🔁 자동재생: {'ON' if autoplay_mode[gid] else 'OFF'}")

    @bot.command(name="대기열")
    async def queue(ctx):
        q = queues.get(ctx.guild.id, [])
        if not q:
            return await ctx.send("대기열이 비어있습니다.")
        desc = '\n'.join(f"{i+1}. {x['title']}" for i, x in enumerate(q))
        await ctx.send(f"🎶 대기열:\n{desc}")

    @bot.command(name="반복")
    async def loop(ctx):
        gid = ctx.guild.id
        loop_mode[gid] = not loop_mode.get(gid, False)
        await ctx.send(f"🔂 반복 모드: {'ON' if loop_mode[gid] else 'OFF'}")

    # 자동 퇴장 로직
    @bot.event
    async def on_voice_state_update(member, before, after):
        if member.bot:
            return  # 봇은 무시

        # 들어간 채널 또는 나간 채널
        vc = after.channel or before.channel
        if not vc:
            return

        # 해당 서버의 현재 봇 voice client
        voice_client = member.guild.voice_client
        if not voice_client or voice_client.channel != vc:
            return  # 봇이 연결된 채널이 아니면 무시

        gid = member.guild.id
        non_bot_members = [m for m in vc.members if not m.bot]

        if non_bot_members:
            # ✅ 유저가 들어옴 → 자동 퇴장 타이머 취소
            if task := idle_timers.pop(gid, None):
                task.cancel()
                print(f"[INFO] 인원 복귀 감지 → 자동퇴장 타이머 취소됨 (GID: {gid})")
        else:
            # ✅ 유저가 모두 나감 → 자동 퇴장 타이머 등록
            if gid not in idle_timers:
                idle_timers[gid] = asyncio.create_task(auto_disconnect_if_empty(gid, voice_client, delay=IDLE_TIMEOUT))
                print(f"[INFO] 유저 전원 퇴장 감지 → 자동퇴장 타이머 등록됨 (GID: {gid})")
        
# ─── 대기열 UI 함수 ────────────────────────────────────────
class DeleteSelectedButton(discord.ui.Button):
    def __init__(self, gid, view):
        super().__init__(label="선택삭제", style=discord.ButtonStyle.danger, row=4)
        self.gid = gid
        self.view_ref = view

    async def callback(self, inter: discord.Interaction):
        indexes = sorted(self.view_ref.selected_indexes, reverse=True)
        for i in indexes:
            if 0 <= i < len(queues.get(self.gid, [])):
                queues[self.gid].pop(i)
        self.view_ref.selected_indexes = []
        await inter.response.edit_message(embed=build_queue_embed(self.gid, self.view_ref.page), view=QueueView(self.gid, self.view_ref.page))


class DeleteAllButton(discord.ui.Button):
    def __init__(self, gid):
        super().__init__(label="전체삭제", style=discord.ButtonStyle.danger, row=4)
        self.gid = gid

    async def callback(self, inter: discord.Interaction):
        queues[self.gid].clear()
        await inter.response.edit_message(embed=build_queue_embed(self.gid), view=QueueView(self.gid))


class QueueSelectBox(discord.ui.Select):
    def __init__(self, gid, items, start_index, view):
        self.gid = gid
        self.view_ref = view
        options = [
            discord.SelectOption(label=f"{start_index + i + 1}. {item['title'][:95]}", value=str(start_index + i))
            for i, item in enumerate(items)
        ]
        super().__init__(placeholder="삭제할 곡 선택 (복수 가능)", min_values=1, max_values=len(options), options=options)

    async def callback(self, inter: discord.Interaction):
        self.view_ref.selected_indexes = list(map(int, self.values))
        await inter.response.send_message("✅ 삭제할 항목이 선택되었습니다. 아래 '선택삭제'를 눌러주세요.", ephemeral=True)


class CloseQueueButton(discord.ui.Button):
    def __init__(self):
        super().__init__(label="대기열닫기", style=discord.ButtonStyle.secondary, row=4)

    async def callback(self, inter: discord.Interaction):
        await inter.message.delete()


class PrevPageButton(discord.ui.Button):
    def __init__(self, gid, page):
        super().__init__(label="⬅ 이전", style=discord.ButtonStyle.primary, row=4)
        self.gid = gid
        self.page = page

    async def callback(self, inter: discord.Interaction):
        await inter.response.edit_message(embed=build_queue_embed(self.gid, self.page), view=QueueView(self.gid, self.page))


class NextPageButton(discord.ui.Button):
    def __init__(self, gid, page):
        super().__init__(label="다음 ➡", style=discord.ButtonStyle.primary, row=4)
        self.gid = gid
        self.page = page

    async def callback(self, inter: discord.Interaction):
        await inter.response.edit_message(embed=build_queue_embed(self.gid, self.page), view=QueueView(self.gid, self.page))


def build_queue_embed(gid, page=0):
    queue = queues.get(gid, [])
    embed = discord.Embed(title="🎵 대기열 목록", color=0x00AAFF)
    start = page * 25
    end = start + 25
    items = queue[start:end]

    lines = [f"{start + i + 1}. {item['title']}" for i, item in enumerate(items)]
    embed.description = "\n".join(lines)
    embed.set_footer(text=f"{start+1}~{min(end, len(queue))} / 전체 {len(queue)}곡")
    return embed


class QueueView(discord.ui.View):
    def __init__(self, gid, page=0):
        super().__init__(timeout=None)
        self.gid = gid
        self.page = page
        self.queue = queues.get(gid, [])
        self.items_per_page = 25

        start = page * self.items_per_page
        end = start + self.items_per_page
        self.page_items = self.queue[start:end]
        self.selected_indexes = []

        # ❌ DeleteSingleButton 완전 제거됨

        # ✅ 선택 삭제/전체 삭제/닫기/페이지 전환
        if self.page_items:
            self.add_item(QueueSelectBox(gid, self.page_items, start, self))
        self.add_item(DeleteSelectedButton(gid, self))
        self.add_item(DeleteAllButton(gid))
        self.add_item(CloseQueueButton())

        if page > 0:
            self.add_item(PrevPageButton(gid, page - 1))
        if end < len(self.queue):
            self.add_item(NextPageButton(gid, page + 1))