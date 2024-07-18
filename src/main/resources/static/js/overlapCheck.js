$(document).ready(function () {
    var isUserIdValid = false;
    var isEmailValid = false;
    var isNicknameValid = false;

    function id_overlap_check() {
        var user_id_input = document.querySelector('input[name="user_id"]');
        if (!user_id_input) return;

        if (user_id_input.value === '') {
            alert('아이디를 입력해주세요.');
            isUserIdValid = false;
            updateSubmitButtonState();
            return;
        }
        $.ajax({
            url: "/login/check-id",
            data: { 'user_id': user_id_input.value },
            datatype: 'json',
            success: function (data) {
                if (data) {
                    alert("이미 존재하는 아이디 입니다.");
                    user_id_input.focus();
                    isUserIdValid = false;
                } else {
                    alert("사용가능한 아이디 입니다.");
                    isUserIdValid = true;
                }
                updateSubmitButtonState();
            },
            error: function (error) {
                console.error("Error:", error);
                alert("아이디 중복 확인 중 오류가 발생했습니다.");
                isUserIdValid = false;
                updateSubmitButtonState();
            }
        });
    }

    function email_overlap_check() {
        var email_input = document.querySelector('input[name="email"]');
        if (!email_input) return;

        if (email_input.value === '') {
            alert('이메일을 입력해주세요.');
            isEmailValid = false;
            updateSubmitButtonState();
            return;
        }
        $.ajax({
            url: "/login/check-email",
            data: { 'email': email_input.value },
            datatype: 'json',
            success: function (data) {
                if (data) {
                    alert("이미 존재하는 이메일 입니다.");
                    email_input.focus();
                    isEmailValid = false;
                } else {
                    alert("사용가능한 이메일 입니다.");
                    isEmailValid = true;
                }
                updateSubmitButtonState();
            },
            error: function (error) {
                console.error("Error:", error);
                alert("이메일 중복 확인 중 오류가 발생했습니다.");
                isEmailValid = false;
                updateSubmitButtonState();
            }
        });
    }

    function user_nikname_overlap_check() {
        var user_nikname_input = document.querySelector('input[name="user_nikname"]');
        if (!user_nikname_input) return;

        if (user_nikname_input.value === '') {
            alert('닉네임을 입력해주세요.');
            isNicknameValid = false;
            updateSubmitButtonState();
            return;
        }
        $.ajax({
            url: "/login/check-nikname",
            data: { 'user_nikname': user_nikname_input.value },
            datatype: 'json',
            success: function (data) {
                if (data) {
                    alert("이미 존재하는 닉네임 입니다.");
                    user_nikname_input.focus();
                    isNicknameValid = false;
                } else {
                    alert("사용가능한 닉네임 입니다.");
                    isNicknameValid = true;
                }
                updateSubmitButtonState();
            },
            error: function (error) {
                console.error("Error:", error);
                alert("닉네임 중복 확인 중 오류가 발생했습니다.");
                isNicknameValid = false;
                updateSubmitButtonState();
            }
        });
    }

    function updateSubmitButtonState() {
        var submitButton = document.querySelector('input[type="submit"]');
        if (isUserIdValid && isEmailValid && isNicknameValid) {
            submitButton.disabled = false;
        } else {
            submitButton.disabled = true;
        }
    }

    var passwordInput = document.querySelector('input[name="pwd"]');
    var confirmPasswordInput = document.querySelector('input[name="pwd_confirm"]');
    if (passwordInput && confirmPasswordInput) {
        passwordInput.addEventListener('keyup', checkPasswordMatch);
        confirmPasswordInput.addEventListener('keyup', checkPasswordMatch);
    }

    var form = document.querySelector('form[name="frmBusinessLogin"]');
    if (form) {
        form.addEventListener('submit', function (e) {
            if (!isUserIdValid || !isEmailValid || !isNicknameValid) {
                alert('중복 확인을 완료해주세요.');
                e.preventDefault();
            }
        });
    }

    var form = document.querySelector('form[name="frmMemberLogin"]');
    if (form) {
        form.addEventListener('submit', function (e) {
            if (!isUserIdValid || !isEmailValid || !isNicknameValid) {
                alert('중복 확인을 완료해주세요.');
                e.preventDefault();
            }
        });
    }

    window.id_overlap_check = id_overlap_check;
    window.email_overlap_check = email_overlap_check;
    window.user_nikname_overlap_check = user_nikname_overlap_check;

    updateSubmitButtonState();
});

function checkPasswordMatch() {
    var password = document.querySelector('input[name="pwd"]').value;
    var confirmPassword = document.querySelector('input[name="pwd_confirm"]').value;

    if (password === confirmPassword) {
        isPasswordMatch = true;
    } else {
        alert("비밀번호가 일치하지 않습니다.");
        isPasswordMatch = false;
    }
    updateSubmitButtonState();
}
