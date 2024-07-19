document.addEventListener('DOMContentLoaded', function() {
    let isEmailValid = false;
    let isNicknameValid = false;

    //제출 버튼 상태 업데이트 함수 - 모두 유효할 때만 제출 버튼 활성화
    function updateSubmitButtonState() {
        let submitButtons = document.querySelectorAll('input[type="submit"]');
        submitButtons.forEach(function(submitButton) {
            submitButton.disabled = !(isEmailValid && isNicknameValid);
        });
        console.log('Submit button state updated:', isEmailValid, isNicknameValid);
    }

    //제출 버튼 비활성화 시 알림 함수
    function showAlertIfSubmitDisabled() {
        if (!isEmailValid || !isNicknameValid) {
            alert('다시 수정 해주세요.');
        }
    }

    //주어진 선택자에 해당 요소가 존재하면 이벤트 리스너 추가
    function addEventListenerIfExists(selector, event, handler) {
        const element = document.querySelector(selector);
        if (element) {
            element.addEventListener(event, handler);
        }
    }

    //이메일 중복 확인
    function email_overlap_check() {
        let email = document.querySelector('input[name="email"]').value;
        if (email === '') {
            alert('이메일을 입력해주세요.');
            return;
        }
        $.ajax({
            url: "/mypage/check-email",
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

    //닉네임 중복 확인
    function user_nikname_overlap_check() {
        let nickname = document.querySelector('input[name="user_nikname"]').value;
        let origin_nikname  = document.querySelector('input[name="origin_nikname"]').value;
        if (nickname === '') {
            alert('닉네임을 입력해주세요.');
            return;
        }
        else if (nickname === origin_nikname) {
            alert('기존 닉네임과 동일합니다.');
            return;
        }
        $.ajax({
            url: "/mypage/check-nikname",
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

    //각 요소 확인 버튼에 이벤트 리스너 추가
    addEventListenerIfExists('.user_email_overlap_button', 'click', email_overlap_check);
    addEventListenerIfExists('.user_nikname_overlap_button', 'click', user_nikname_overlap_check);

    //폼 제출 시 유효성 검사
    const frmMemberLogin = document.querySelector('form[name="loginModifyForm"]');
    if (frmMemberLogin) {
        frmMemberLogin.addEventListener('submit', function(e) {
            if (!isEmailValid || !isNicknameValid) {
                alert('중복 확인 해주세요.');
                e.preventDefault();
            }
        });
    }

    //제출 버튼 클릭 시 비활성화면 알림, 제출 막기
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