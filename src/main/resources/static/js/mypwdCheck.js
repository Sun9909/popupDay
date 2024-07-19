document.addEventListener('DOMContentLoaded', function() {
    let isPasswordMatch = false;

    //제출 버튼 상태 업데이트 함수 - 모두 유효할 때만 제출 버튼 활성화
    function updateSubmitButtonState() {
        let submitButtons = document.querySelectorAll('input[type="submit"]');
        submitButtons.forEach(function(submitButton) {
            submitButton.disabled = !(isPasswordMatch);
        });
        console.log('Submit button state updated:', isPasswordMatch);
    }

    //제출 버튼 비활성화 시 알림 함수
    function showAlertIfSubmitDisabled() {
        if (!isPasswordMatch) {
            alert('다시 수정 해주세요.');
        }
    }

    //주어진 서택자에 해당 요소가 존재하면 이벤트 리스너 추가
    // function addEventListenerIfExists(selector, event, handler) {
    //     const element = document.querySelector(selector);
    //     if (element) {
    //         element.addEventListener(event, handler);
    //     }
    // }

    function checkOriginalPassword() {
        let originalPwd = document.querySelector('input[name="password"]'); //현재 비번 가져오기

        if (originalPwd.type === "password") {
            originalPwd.type = "text";
        } else {
            originalPwd.type = "password";
        }
    }
    //입력된 비밀번호와 확인 비밀번호 일치 여부 확인
    function checkPasswordMatch() {
        let password = document.querySelector('input[name="newpwd"]').value;
        let newPassword = document.querySelector('input[name="newpwdcheck"]').value;
        if (password === newPassword) {
            alert("비밀번호가 일치합니다.");
            isPasswordMatch = true;
        } else {
            alert("변경된 비밀번호가 일치하지 않습니다.");
            isPasswordMatch = false;
        }
        updateSubmitButtonState();
    }

    //각 요소 확인 버튼에 이벤트 리스너 추가
    document.querySelector('.pwd_origin_button').addEventListener('click', checkOriginalPassword);
    document.querySelector('.pwd_check_button').addEventListener('click', checkPasswordMatch);

    //폼 제출 시 유효성 검사
    const passwordForm = document.querySelector('form[name="passwordForm"]');
    if (passwordForm) {
        passwordForm.addEventListener('submit', function(e) {
            if (!isPasswordMatch) {
                alert('비밀번호 확인을 해주세요.');
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