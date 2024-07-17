document.addEventListener('DOMContentLoaded', function() {
    let isUserIdValid = false;
    let isEmailValid = false;
    let isNicknameValid = false;
    let isPasswordMatch = false;
    let isCorpChecked = false; // 사업자 등록번호 확인 여부를 저장하는 변수 추가

    function updateSubmitButtonState() {
        let submitButtons = document.querySelectorAll('input[type="submit"]');
        submitButtons.forEach(function(submitButton) {
            submitButton.disabled = !(isUserIdValid && isEmailValid && isNicknameValid && isPasswordMatch && isCorpChecked);
        });
        console.log('Submit button state updated:', isUserIdValid, isEmailValid, isNicknameValid, isPasswordMatch, isCorpChecked);
    }

    function showAlertIfSubmitDisabled() {
        if (!isUserIdValid || !isEmailValid || !isNicknameValid || !isPasswordMatch || !isCorpChecked) {
            alert('중복 확인 및 비밀번호 확인을 해주세요.');
        }
    }

    function addEventListenerIfExists(selector, event, handler) {
        const element = document.querySelector(selector);
        if (element) {
            element.addEventListener(event, handler);
        }
    }

    addEventListenerIfExists('.id_overlap_button', 'click', function() {
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
    });

    addEventListenerIfExists('.email_overlap_button', 'click', function() {
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
    });

    addEventListenerIfExists('.user_nikname_overlap_button', 'click', function() {
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
    });

    addEventListenerIfExists('.pwd_overlap_button', 'click', function() {
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
    });

    function corp_chk() {
        $(".corp_reg").val($(".corp_reg").val().replace(/[^0-9]/g, ""));
        business_number = $(".corp_reg").val();

        if(!business_number) {
            alert("사업자등록번호를 입력해주세요.");
            return false;
        }

        var data = {
            "b_no": [business_number]
        };

        $.ajax({
            url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=gh2AUehoQ3GJd40ZAezpHi4%2Bvh%2Bn%2Fh0EISraeBwfkwbSf9j4ZZHDPyDVW5rNnYSUj3IkfjftqulQ%2FC45Kg8lOQ%3D%3D",
            type: "POST",
            data: JSON.stringify(data),
            dataType: "JSON",
            traditional: true,
            contentType: "application/json; charset:UTF-8",
            accept: "application/json",
            success: function(result) {
                console.log(result);
                if(result.match_cnt == "1") {
                    console.log("success");
                    alert("확인되셨습니다.");
                    isCorpChecked = true;  // 사업자 등록번호 확인 상태를 true로 변경
                } else {
                    console.log("fail");
                    alert(result.data[0]["tax_type"]);
                    isCorpChecked = false;
                }
                updateSubmitButtonState();
            },
            error: function(result) {
                console.log("error");
                console.log(result.responseText);
                isCorpChecked = false;
                updateSubmitButtonState();
            }
        });
    }

    addEventListenerIfExists('#corp_button', 'click', corp_chk);

    const frmMemberLogin = document.querySelector('form[name="frmMemberLogin"]');
    if (frmMemberLogin) {
        frmMemberLogin.addEventListener('submit', function(e) {
            if (!isUserIdValid || !isEmailValid || !isNicknameValid || !isPasswordMatch || !isCorpChecked) {
                alert('중복 확인 및 비밀번호 확인을 해주세요.');
                e.preventDefault();
            }
        });
    }

    const frmBusinessLogin = document.querySelector('form[name="frmBusinessLogin"]');
    if (frmBusinessLogin) {
        frmBusinessLogin.addEventListener('submit', function(e) {
            if (!isUserIdValid || !isEmailValid || !isNicknameValid || !isPasswordMatch || !isCorpChecked) {
                alert('중복 확인 및 비밀번호 확인을 해주세요.');
                e.preventDefault();
            }
        });
    }

    let submitButtons = document.querySelectorAll('input[type="submit"]');
    submitButtons.forEach(function(submitButton) {
        submitButton.addEventListener('click', function(e) {
            showAlertIfSubmitDisabled();
        });
    });

    updateSubmitButtonState();
});
