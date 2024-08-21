document.addEventListener('DOMContentLoaded', function() {
    // 빈 리스트를 초기화합니다.
    var month = [];
    var count = [];
    // 각 요소를 가져와서 리스트에 추가합니다.
    document.querySelectorAll('.viewer-list').forEach(function(element) {
        month.push(element.getAttribute('data-month'));
        count.push(element.getAttribute('data-hit-count'));
    });

    // 그래프를 그리기 위한 데이터와 옵션 설정(팝업 전체 방문자 통계)
    const ctx = document.getElementById('myChart').getContext('2d');

    const myChart = new Chart(ctx, {
        type: 'line', // 그래프의 종류: 'line', 'bar', 'pie' 등
        data: {
            labels: month, // x축 레이블
            datasets: [{
                label: '팝업 조회 수', // 데이터셋 레이블
                data: count, // y축 데이터
                borderColor: 'rgba(75, 192, 192, 1)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderWidth: 1
            }]
        },
        options: {
            plugins: {
                title: {
                    display: true, // 제목 표시 여부
                    text: '방문자 수', // 제목 내용
                    color: '#333', // 제목 색상
                    font: {
                        size: 18, // 제목 폰트 크기
                        family: 'Helvetica Neue', // 제목 폰트 패밀리
                        weight: 'bold' // 제목 폰트 두께
                    },
                    padding: {
                        top: 10,
                        bottom: 20
                    },
                    backgroundColor:'white'
                }
            },
            scales: {
                x: { // x축 설정
                    title: {
                        display: true, // 제목 표시 여부
                        text: '조회 날짜' // 제목 내용
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: '조회 수'
                    },
                    beginAtZero: true
                }
            }
        }
    });
});

var chartVisible = false;
var myChart2 = null; // 차트 인스턴스를 저장할 변수

document.getElementById("view-info").addEventListener('click', function() {
    var canvas = document.getElementById('visitAge');
    var ctx2 = canvas.getContext('2d');

    if (chartVisible) {
        // 차트를 숨깁니다.
        canvas.style.display = 'none';
        console.log('Canvas display after hiding:', canvas.style.display);
        if (myChart2) {
            myChart2.destroy(); // 기존 차트를 제거합니다.
            myChart2 = null;
        }
    } else {
        // 차트를 표시합니다.
        canvas.style.display = 'block';

        // 데이터와 차트 옵션을 설정합니다.
        var count = [
            [0,0,0,0,0,0], // 남성
            [0,0,0,0,0,0] // 여성
        ];
        var month = ['10대','20대','30대','40대','50대','60대 이상'];
        var gender = ['남성','여성'];

        // 각 요소를 가져와서 리스트에 추가합니다.
        document.querySelectorAll('.viewer-info').forEach(function(element){

            var dataMonth = parseInt(element.getAttribute('data-birth'),10);
            console.log(dataMonth);
            var currentYear = new Date().getFullYear();
            console.log(currentYear)
            var age = currentYear - dataMonth;
            var user_gender = element.getAttribute('data-gender');
            var user_count = parseInt(element.getAttribute('data-count'),10);
            if(age >= 60){
                switch(user_gender){
                    case '남성':
                        count[0][5]+= user_count;
                        break;
                    case '여성':
                        count[1][5]+= user_count;
                        break;
                }
            }else if(age >= 50){
                switch(user_gender){
                    case '남성':
                        count[0][4]+= user_count;
                        break;
                    case '여성':
                        count[1][4]+= user_count;
                        break;
                }
            }else if(age >= 40){
                switch(user_gender){
                    case '남성':
                        count[0][3]+= user_count;
                        break;
                    case '여성':
                        count[1][3]+= user_count;
                        break;
                }
            }else if(age >= 30){
                switch(user_gender){
                    case '남성':
                        count[0][2]+= user_count;
                        break;
                    case '여성':
                        count[1][2]+= user_count;
                        break;
                }
            }else if(age >= 20){
                switch(user_gender){
                    case '남성':
                        count[0][1]+= user_count;
                        break;
                    case '여성':
                        count[1][1]+= user_count;
                        break;
                }
            }else{
                switch(user_gender){
                    case '남성':
                        count[0][0]+= user_count;
                        break;
                    case '여성':
                        count[1][0]+= user_count;
                        break;
                }
            }
        });

        // 차트를 생성합니다.
        myChart2 = new Chart(ctx2, {
            type: 'bar',
            data: {
                labels: month,
                 datasets: [{
                            type:'bar',
                            label: '남성',
                            data: count[0],
                            backgroundColor: 'rgba(54, 162, 235, 0.6)', // 남성 막대 색상
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }, {
                            type:'bar',
                            label: '여성',
                            data: count[1],
                            backgroundColor: 'rgba(255, 99, 132, 0.6)', // 여성 막대 색상
                            borderColor: 'rgba(255, 99, 132, 1)',
                            borderWidth: 1
                        }]
                    },
            options: {
                plugins: {
                    title: {
                        display: true, // 제목 표시 여부
                        text: '방문자 상세정보', // 제목 내용
                        color: '#333', // 제목 색상
                        font: {
                            size: 18, // 제목 폰트 크기
                            family: 'Helvetica Neue', // 제목 폰트 패밀리
                            weight: 'bold' // 제목 폰트 두께
                        },
                        padding: {
                            top: 10,
                            bottom: 20
                        }
                    }
                },
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: '연령대'
                        }
                    },
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                            text: '조회 수'
                        }
                    }
                }
            }
        });
    }
    // 차트의 표시 여부를 토글합니다.
    chartVisible = !chartVisible;

});