package flower.popupday.notice.notice.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("noticeDTO")
public class NoticeDTO {
    private Long  notice_id;
    private Long user_id;
    private String title;
    private String content;
    private Date date_created;
    private String write;

    // NoiticeDTO 선언
    public NoticeDTO() {}

    // 모든 필드를 포함하는 생성자
    public  NoticeDTO(Long notice_id, Long user_id, String title, String content, Date date_created, String write) {
        this.notice_id = notice_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.date_created = date_created;
        this.write = write;
    }

    //  Getter,Setter 생상
    public long getNotice_id() {return notice_id; }
    public void setNotice_id(long notice_id) {
        this.notice_id = notice_id;
    }

    public Long getUser_id() {return user_id;}

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    // 값이 전달 안 될때 toString 호출해서 확인.
    @Override
    public String toString() {
        return "NoticeDTO{" +
                "notice_id=" + notice_id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date_created=" + date_created +
                ", write='" + write + '\'' +
                '}';
    }


}
