document.addEventListener('DOMContentLoaded', function() {
    let isUserIdValid = false;
    let isEmailValid = false;
    let isNicknameValid = false;
    let isPasswordMatch = false;
    let isCorpChecked = false;

    function updateSubmitButtonState() {
        let submitButtons = document.querySelectorAll('input[type="submit"]');
        submitButtons.forEach(function(submitButton) {
            submitButton.disabled = !(isUserIdValid && isEmailValid && isNicknameValid && isPasswordMatch && isCorpChecked);
        });
        console.log('Submit button state updated:', isUserIdValid, isEmailValid, isNicknameValid, isPasswordMatch, isCorpChecked);
    }

    function showAlertIfSubmitDisabled() {
        if (!isUserIdValid || !isEmailValid || !isNicknameValid || !isPasswordMatch || !isCorpChecked) {
            Swal.fire({
                icon: 'warning',
                title: '입력 확인',
                text: '중복 확인 및 비밀번호 확인을 해주세요.'
            });
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
            Swal.fire({
                icon: 'warning',
                title: '이메일 입력',
                text: '이메일을 입력해주세요.'
            });
            return;
        }
        $.ajax({
            url: "/login/check-email",
            data: { 'email': email },
            datatype: 'json',
            success: function(data) {
                if (data) {
                    Swal.fire({
                        icon: 'error',
                        title: '중복 확인',
                        text: '이미 존재하는 이메일 입니다.'
                    });
                    isEmailValid = false;
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: '사용 가능',
                        text: '사용가능한 이메일 입니다.'
                    });
                    isEmailValid = true;
                }
                updateSubmitButtonState();
            },
            error: function() {
                Swal.fire({
                    icon: 'error',
                    title: '오류',
                    text: '이메일 중복 확인 중 오류가 발생했습니다.'
                });
                isEmailValid = false;
                updateSubmitButtonState();
            }
        });
    }

    function id_overlap_check() {
        let userId = document.querySelector('input[name="user_id"]').value;
        if (userId === '') {
            Swal.fire({
                icon: 'warning',
                title: '아이디 입력',
                text: '아이디를 입력해주세요.'
            });
            return;
        }
        $.ajax({
            url: "/login/check-id",
            data: { 'user_id': userId },
            datatype: 'json',
            success: function(data) {
                if (data) {
                    Swal.fire({
                        icon: 'error',
                        title: '중복 확인',
                        text: '이미 존재하는 아이디 입니다.'
                    });
                    isUserIdValid = false;
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: '사용 가능',
                        text: '사용가능한 아이디 입니다.'
                    });
                    isUserIdValid = true;
                }
                updateSubmitButtonState();
            },
            error: function() {
                Swal.fire({
                    icon: 'error',
                    title: '오류',
                    text: '아이디 중복 확인 중 오류가 발생했습니다.'
                });
                isUserIdValid = false;
                updateSubmitButtonState();
            }
        });
    }

    function checkPasswordMatch() {
        let password = document.querySelector('input[name="pwd"]').value;
        let confirmPassword = document.querySelector('input[name="pwd_confirm"]').value;
        if (password === confirmPassword) {
            Swal.fire({
                icon: 'success',
                title: '비밀번호 확인',
                text: '비밀번호가 일치합니다.'
            });
            isPasswordMatch = true;
        } else {
            Swal.fire({
                icon: 'error',
                title: '비밀번호 불일치',
                text: '비밀번호가 일치하지 않습니다.'
            });
            isPasswordMatch = false;
        }
        updateSubmitButtonState();
    }

    function user_nikname_overlap_check() {
        let nickname = document.querySelector('input[name="user_nikname"]').value;
        if (nickname === '') {
            Swal.fire({
                icon: 'warning',
                title: '닉네임 입력',
                text: '닉네임을 입력해주세요.'
            });
            return;
        }
        $.ajax({
            url: "/login/check-nikname",
            data: { 'user_nikname': nickname },
            datatype: 'json',
            success: function(data) {
                if (data) {
                    Swal.fire({
                        icon: 'error',
                        title: '중복 확인',
                        text: '이미 존재하는 닉네임 입니다.'
                    });
                    isNicknameValid = false;
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: '사용 가능',
                        text: '사용가능한 닉네임 입니다.'
                    });
                    isNicknameValid = true;
                }
                updateSubmitButtonState();
            },
            error: function() {
                Swal.fire({
                    icon: 'error',
                    title: '오류',
                    text: '닉네임 중복 확인 중 오류가 발생했습니다.'
                });
                isNicknameValid = false;
                updateSubmitButtonState();
            }
        });
    }

    function corp_chk() {
        $(".corp_reg").val($(".corp_reg").val().replace(/[^0-9]/g, ""));
        let business_number = $(".corp_reg").val();

        if (!business_number) {
            Swal.fire({
                icon: 'warning',
                title: '사업자등록번호 입력',
                text: '사업자등록번호를 입력해주세요.'
            });
            return false;
        }

        var data = {
            "b_no": [business_number]
        };

        $.ajax({
            url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=gh2AUehoQ3GJd40ZAezpHi4%2Bvh%2Bn%2Fh0EISraeBwfkwbSf9j4ZZHDPyDVW5rNnYSUj3IkfjftqulQ%2FC45Kg8lOQ%3D%3D",  // serviceKey 값을 입력
            type: "POST",
            data: JSON.stringify(data),
            dataType: "JSON",
            traditional: true,
            contentType: "application/json; charset:UTF-8",
            accept: "application/json",
            success: function(result) {
                console.log(result);
                if (result.match_cnt == "1") {
                    Swal.fire({
                        icon: 'success',
                        title: '확인 완료',
                        text: '확인되셨습니다.'
                    });
                    isCorpChecked = true;
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: '확인 실패',
                        text: result.data[0]["tax_type"]
                    });
                    isCorpChecked = false;
                }
                updateSubmitButtonState();
            },
            error: function(result) {
                Swal.fire({
                    icon: 'error',
                    title: '오류',
                    text: result.responseText
                });
                isCorpChecked = false;
                updateSubmitButtonState();
            }
        });
    }

    addEventListenerIfExists('.email_overlap_button', 'click', email_overlap_check);
    addEventListenerIfExists('.id_overlap_button', 'click', id_overlap_check);
    addEventListenerIfExists('.pwd_overlap_button', 'click', checkPasswordMatch);
    addEventListenerIfExists('.user_nikname_overlap_button', 'click', user_nikname_overlap_check);
    addEventListenerIfExists('#corp_button', 'click', corp_chk);

    const frmBusinessLogin = document.querySelector('form[name="frmBusinessLogin"]');
    if (frmBusinessLogin) {
        frmBusinessLogin.addEventListener('submit', function(e) {
            if (!isUserIdValid || !isEmailValid || !isNicknameValid || !isPasswordMatch || !isCorpChecked) {
                Swal.fire({
                    icon: 'warning',
                    title: '입력 확인',
                    text: '중복 확인 및 비밀번호 확인을 해주세요.'
                });
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
