
const popup_id = 18; // 팝업 ID

async function fetchStatsData() {
    try {
        const response = await fetch('/business/StatsList.do?popup_id=${popup_id}'); // popup_id를 실제 값으로 설정
        const data = await response.json();

        // 날짜와 조회수를 저장할 배열
        const dates = [];
        const counts = [];

        // 반복문을 통해 데이터를 분리하여 배열에 저장
        data.forEach(count => {
            dates.push(count.month); // DTO에서 날짜를 가져온다고 가정
            counts.push(count.hit_count); // DTO에서 조회수를 가져온다고 가정
        });

        // 차트 생성
        const ctx = document.getElementById('myChart').getContext('2d');
        const statsChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: dates,
                datasets: [{
                    label: '조회수',
                    data: counts,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderWidth: 1
                }]
            },
            options: {
                plugins: {
                    title: {
                        display: true, // 제목 표시 여부
                        text: '총 방문자 통계', // 제목 내용
                        color: '#333', // 제목 색상
                        font: {
                            size: 18, // 제목 폰트 크기
                            family: 'Helvetica Neue', // 제목 폰트 패밀리
                            weight: 'bold' // 제목 폰트 두께
                        },
                        padding: {
                            top: 10,
                            bottom: 30
                        }
                    }
                },
                scales: {
                     x: { // x축 설정
                            title: {
                                display: true, // 제목 표시 여부
                                text: '2024년', // 제목 내용
                                color: 'black', // 제목 색상
                                font: {
                                    size: 14, // 제목 폰트 크기
                                    family: 'Helvetica Neue', // 제목 폰트 패밀리
                                    weight: 'bold' // 제목 폰트 두께
                                }
                            }
                     },
                    y: {
                        title: {
                            display: true,
                            text: '조회 수(명)',
                            color: 'black',
                            font: {
                                size: 14, // 제목 폰트 크기
                                family: 'Helvetica Neue', // 제목 폰트 패밀리
                                weight: 'bold' // 제목 폰트 두께
                            }
                        },
                        beginAtZero: true
                    }
                }
            }
        });
    } catch (error) {
        console.error('데이터를 불러오는데 실패했습니다.', error);
    }
}

// 페이지 로드 시 데이터 가져오기
window.onload = fetchStatsData;