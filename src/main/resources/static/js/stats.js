// 그래프를 그리기 위한 데이터와 옵션 설정
const ctx = document.getElementById('myChart').getContext('2d');

const myChart = new Chart(ctx, {
    type: 'line', // 그래프의 종류: 'line', 'bar', 'pie' 등
    data: {
        labels: ['1월', '2월', '3월', '4월', '5월','6월','7월','8월','9월','10월','11월','12월'], // x축 레이블
        datasets: [{
            label: 'My First Dataset', // 데이터셋 레이블
            data: [10, 20, 30, 40, 50, 20, 40, 10, 5, 20, 30, 5], // y축 데이터
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