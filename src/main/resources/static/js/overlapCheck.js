$(document).ready(function () {
    // 아이디 중복 확인 함수
    function id_overlap_check() {
        var user_id_input = document.querySelector('input[name="user_id"]'); // 'user_id' input 요소를 선택

        // 아이디 입력 값이 비어있는지 확인
        if (user_id_input.value === '') {
            alert('아이디를 입력해주세요.');
            return;
        }

        // AJAX 요청을 통해 서버에 아이디 중복 확인 요청
        $.ajax({
            url: "/login/check-id", // 아이디 중복 확인을 위한 서버 URL
            data: {
                'user_id': user_id_input.value // 입력된 아이디 값
            },
            datatype: 'json', // 응답 데이터 타입
            success: function (data) {
                // 서버 응답이 중복 아이디 존재 여부를 나타냄
                if (data) {
                    alert("이미 존재하는 아이디 입니다."); // 중복 아이디인 경우 경고 메시지
                    user_id_input.focus(); // 아이디 입력란에 포커스
                } else {
                    alert("사용가능한 아이디 입니다."); // 중복되지 않은 경우 메시지
                }
            },
            error: function (error) {
                console.error("Error:", error); // 오류 발생 시 콘솔에 오류 출력
                alert("아이디 중복 확인 중 오류가 발생했습니다."); // 사용자에게 오류 메시지 표시
            }
        });
    }

    // 이메일 중복 확인 함수
    function email_overlap_check() {
        var email_input = document.querySelector('input[name="email"]'); // 'email' input 요소를 선택

        // 이메일 입력 값이 비어있는지 확인
        if (email_input.value === '') {
            alert('이메일을 입력해주세요.');
            return;
        }

        // AJAX 요청을 통해 서버에 이메일 중복 확인 요청
        $.ajax({
            url: "/login/check-email", // 이메일 중복 확인을 위한 서버 URL
            data: {
                'email': email_input.value // 입력된 이메일 값
            },
            datatype: 'json', // 응답 데이터 타입
            success: function (data) {
                // 서버 응답이 중복 이메일 존재 여부를 나타냄
                if (data) {
                    alert("이미 존재하는 이메일 입니다."); // 중복 이메일인 경우 경고 메시지
                    email_input.focus(); // 이메일 입력란에 포커스
                } else {
                    alert("사용가능한 이메일 입니다."); // 중복되지 않은 경우 메시지
                }
            },
            error: function (error) {
                console.error("Error:", error); // 오류 발생 시 콘솔에 오류 출력
                alert("이메일 중복 확인 중 오류가 발생했습니다."); // 사용자에게 오류 메시지 표시
            }
        });
    }

    // 닉네임 중복 확인 함수
    function user_nikname_overlap_check() {
        var user_nikname_input = document.querySelector('input[name="user_nikname"]'); // 'user_nikname' input 요소를 선택

        // 닉네임 입력 값이 비어있는지 확인
        if (user_nikname_input.value === '') {
            alert('닉네임을 입력해주세요.');
            return;
        }

        // AJAX 요청을 통해 서버에 닉네임 중복 확인 요청
        $.ajax({
            url: "/login/check-nikname", // 닉네임 중복 확인을 위한 서버 URL
            data: {
                'user_nikname': user_nikname_input.value // 입력된 닉네임 값
            },
            datatype: 'json', // 응답 데이터 타입
            success: function (data) {
                // 서버 응답이 중복 닉네임 존재 여부를 나타냄
                if (data) {
                    alert("이미 존재하는 닉네임 입니다."); // 중복 닉네임인 경우 경고 메시지
                    user_nikname_input.focus(); // 닉네임 입력란에 포커스
                } else {
                    alert("사용가능한 닉네임 입니다."); // 중복되지 않은 경우 메시지
                }
            },
            error: function (error) {
                console.error("Error:", error); // 오류 발생 시 콘솔에 오류 출력
                alert("닉네임 중복 확인 중 오류가 발생했습니다."); // 사용자에게 오류 메시지 표시
            }
        });
    }

    // 중복 확인 함수들을 전역 범위에 할당
    window.id_overlap_check = id_overlap_check;
    window.email_overlap_check = email_overlap_check;
    window.user_nikname_overlap_check = user_nikname_overlap_check;
});
