$(document).ready(function () {
    function id_overlap_check() {
        var user_id_input = document.querySelector('input[name="user_id"]');

        if (user_id_input.value === '') {
            alert('아이디를 입력해주세요.');
            return;
        }

        $.ajax({
            url: "/login/check-id",
            data: {
                'user_id': user_id_input.value
            },
            datatype: 'json',
            success: function (data) {
                if (data) {
                    alert("이미 존재하는 아이디 입니다.");
                    user_id_input.focus();
                } else {
                    alert("사용가능한 아이디 입니다.");
                }
            },
            error: function (error) {
                console.error("Error:", error);
                alert("아이디 중복 확인 중 오류가 발생했습니다.");
            }
        });
    }

    function email_overlap_check() {
        var email_input = document.querySelector('input[name="email"]');

        if (email_input.value === '') {
            alert('이메일을 입력해주세요.');
            return;
        }

        $.ajax({
            url: "/login/check-email",
            data: {
                'email': email_input.value
            },
            datatype: 'json',
            success: function (data) {
                if (data) {
                    alert("이미 존재하는 이메일 입니다.");
                    email_input.focus();
                } else {
                    alert("사용가능한 이메일 입니다.");
                }
            },
            error: function (error) {
                console.error("Error:", error);
                alert("이메일 중복 확인 중 오류가 발생했습니다.");
            }
        });
    }

    function user_nikname_overlap_check() {
        var user_nikname_input = document.querySelector('input[name="user_nikname"]');

        if (user_nikname_input.value === '') {
            alert('닉네임을 입력해주세요.');
            return;
        }

        $.ajax({
            url: "/login/check-nikname",
            data: {
                'user_nikname': user_nikname_input.value
            },
            datatype: 'json',
            success: function (data) {
                if (data) {
                    alert("이미 존재하는 닉네임 입니다.");
                    user_nikname_input.focus();
                } else {
                    alert("사용가능한 닉네임 입니다.");
                }
            },
            error: function (error) {
                console.error("Error:", error);
                alert("닉네임 중복 확인 중 오류가 발생했습니다.");
            }
        });
    }

    window.id_overlap_check = id_overlap_check;
    window.email_overlap_check = email_overlap_check;
    window.user_nikname_overlap_check = user_nikname_overlap_check;
});
