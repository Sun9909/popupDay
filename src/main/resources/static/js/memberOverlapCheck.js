document.addEventListener('DOMContentLoaded', function() {
    let isUserIdValid = false;
    let isEmailValid = false;
    let isNicknameValid = false;
    let isPasswordMatch = false;

    function updateSubmitButtonState() {
        let submitButtons = document.querySelectorAll('input[type="submit"]');
        submitButtons.forEach(function(submitButton) {
            submitButton.disabled = !(isUserIdValid && isEmailValid && isNicknameValid && isPasswordMatch);
        });
        console.log('Submit button state updated:', isUserIdValid, isEmailValid, isNicknameValid, isPasswordMatch);
    }

    function showAlertIfSubmitDisabled() {
        if (!isUserIdValid || !isEmailValid || !isNicknameValid || !isPasswordMatch) {
            alert('중복 확인 및 비밀번호 확인을 해주세요.');
        }
    }

    function addEventListenerIfExists(selector, event, handler) {
        const element = document.querySelector(selector);
        if (element) {
            element.addEventListener(event, handler);
        }
    }

    function email_overlap_check() {
        let email = document.querySelector('input[name="email"]').value;
        if (email === '') {
            alert('이메일을 입력해주세요.');
            return;
        }
        $.ajax({
            url: "/login/check-email",
            data: { 'email': email },
            datatype: 'json',
            success: function(data) {
                if (data) {
                    alert("이미 존재하는 이메일 입니다.");
                    isEmailValid = false;
                } else {
                    alert("사용가능한 이메일 입니다.");
                    isEmailValid = true;
                }
                updateSubmitButtonState();
            },
            error: function() {
                alert("이메일 중복 확인 중 오류가 발생했습니다.");
                isEmailValid = false;
                updateSubmitButtonState();
            }
        });
    }

    function id_overlap_check() {
        let userId = document.querySelector('input[name="user_id"]').value;
        if (userId === '') {
            alert('아이디를 입력해주세요.');
            return;
        }
        $.ajax({
            url: "/login/check-id",
            data: { 'user_id': userId },
            datatype: 'json',
            success: function(data) {
                if (data) {
                    alert("이미 존재하는 아이디 입니다.");
                    isUserIdValid = false;
                } else {
                    alert("사용가능한 아이디 입니다.");
                    isUserIdValid = true;
                }
                updateSubmitButtonState();
            },
            error: function() {
                alert("아이디 중복 확인 중 오류가 발생했습니다.");
                isUserIdValid = false;
                updateSubmitButtonState();
            }
        });
    }

    function checkPasswordMatch() {
        let password = document.querySelector('input[name="pwd"]').value;
        let confirmPassword = document.querySelector('input[name="pwd_confirm"]').value;
        if (password === confirmPassword) {
            alert("비밀번호가 일치합니다.");
            isPasswordMatch = true;
        } else {
            alert("비밀번호가 일치하지 않습니다.");
            isPasswordMatch = false;
        }
        updateSubmitButtonState();
    }

    function user_nikname_overlap_check() {
        let nickname = document.querySelector('input[name="user_nikname"]').value;
        if (nickname === '') {
            alert('닉네임을 입력해주세요.');
            return;
        }
        $.ajax({
            url: "/login/check-nikname",
            data: { 'user_nikname': nickname },
            datatype: 'json',
            success: function(data) {
                if (data) {
                    alert("이미 존재하는 닉네임 입니다.");
                    isNicknameValid = false;
                } else {
                    alert("사용가능한 닉네임 입니다.");
                    isNicknameValid = true;
                }
                updateSubmitButtonState();
            },
            error: function() {
                alert("닉네임 중복 확인 중 오류가 발생했습니다.");
                isNicknameValid = false;
                updateSubmitButtonState();
            }
        });
    }

    addEventListenerIfExists('.email_overlap_button', 'click', email_overlap_check);
    addEventListenerIfExists('.id_overlap_button', 'click', id_overlap_check);
    addEventListenerIfExists('.pwd_overlap_button', 'click', checkPasswordMatch);
    addEventListenerIfExists('.user_nikname_overlap_button', 'click', user_nikname_overlap_check);

    const frmMemberLogin = document.querySelector('form[name="frmMemberLogin"]');
    if (frmMemberLogin) {
        frmMemberLogin.addEventListener('submit', function(e) {
            if (!isUserIdValid || !isEmailValid || !isNicknameValid || !isPasswordMatch) {
                alert('중복 확인 및 비밀번호 확인을 해주세요.');
                e.preventDefault();
            }
        });
    }

    let submitButtons = document.querySelectorAll('input[type="submit"]');
    submitButtons.forEach(function(submitButton) {
        submitButton.addEventListener('click', function(e) {
            if (submitButton.disabled) {
                showAlertIfSubmitDisabled();
                e.preventDefault();
            }
        });
    });

    updateSubmitButtonState();
});
