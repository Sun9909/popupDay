document.addEventListener('DOMContentLoaded', function() {
    let isEmailValid = false;
    let isNicknameValid = false;
    let emailChecked = false;
    let nicknameChecked = false;

    // 제출 버튼 상태 업데이트 함수 - 모두 유효할 때만 제출 버튼 활성화
    function updateSubmitButtonState() {
        let submitButtons = document.querySelectorAll('input[type="submit"]');
        submitButtons.forEach(function(submitButton) {
            submitButton.disabled = !(isEmailValid || isNicknameValid);
        });
        console.log('Submit button state updated:', isEmailValid, isNicknameValid);
    }

    // 제출 버튼 비활성화 시 알림 함수
    function showAlertIfSubmitDisabled() {
        if (!isEmailValid && !isNicknameValid) {
            Swal.fire({
                icon: 'warning',
                title: '다시 수정 해주세요.',
                showConfirmButton: true
            });
        }
    }

    // 주어진 선택자에 해당 요소가 존재하면 이벤트 리스너 추가
    function addEventListenerIfExists(selector, event, handler) {
        const element = document.querySelector(selector);
        if (element) {
            element.addEventListener(event, handler);
        }
    }

    // 이메일 중복 확인
    function email_overlap_check() {
        let email = document.querySelector('input[name="email"]').value;
        let origin_email = document.querySelector('input[name="origin_email"]').value;
        if (email === '') {
            Swal.fire({
                icon: 'warning',
                title: '이메일을 입력해주세요.',
                showConfirmButton: true
            });
            isEmailValid = false;
            emailChecked = false;
            updateSubmitButtonState();
            return;
        } else if (email === origin_email) {
            Swal.fire({
                icon: 'info',
                title: '기존 이메일과 동일합니다.',
                showConfirmButton: true
            });
            isEmailValid = true;
            emailChecked = true;
            updateSubmitButtonState();
            return;
        }
        $.ajax({
            url: "/mypage/check-email",
            data: { 'email': email },
            datatype: 'json',
            success: function(data) {
                if (data) {
                    Swal.fire({
                        icon: 'error',
                        title: '이미 존재하는 이메일 입니다.',
                        showConfirmButton: true
                    });
                    isEmailValid = false;
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: '사용가능한 이메일 입니다.',
                        showConfirmButton: true
                    });
                    isEmailValid = true;
                }
                emailChecked = true;
                updateSubmitButtonState();
            },
            error: function() {
                Swal.fire({
                    icon: 'error',
                    title: '이메일 중복 확인 중 오류가 발생했습니다.',
                    showConfirmButton: true
                });
                isEmailValid = false;
                emailChecked = false;
                updateSubmitButtonState();
            }
        });
    }

    // 닉네임 중복 확인
    function user_nickname_overlap_check() {
        let nickname = document.querySelector('input[name="user_nickname"]').value;
        let origin_nikname  = document.querySelector('input[name="origin_nickname"]').value;

        if (nickname === '') {
            Swal.fire({
                icon: 'warning',
                title: '닉네임을 입력해주세요.',
                showConfirmButton: true
            });
            isNicknameValid = false;
            nicknameChecked = false;
            updateSubmitButtonState();
            return;
        } else if (nickname === origin_nikname) {
            Swal.fire({
                icon: 'info',
                title: '기존 닉네임과 동일합니다.',
                showConfirmButton: true
            });
          
            isNicknameValid = true;
            nicknameChecked = true;
            updateSubmitButtonState();
            return;
        }
        $.ajax({
            url: "/mypage/check-nickname",
            data: { 'user_nickname': nickname },
            datatype: 'json',
            success: function(data) {
                if (data) {
                    Swal.fire({
                        icon: 'error',
                        title: '이미 존재하는 닉네임 입니다.',
                        showConfirmButton: true
                    });
                    isNicknameValid = false;
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: '사용가능한 닉네임 입니다.',
                        showConfirmButton: true
                    });
                    isNicknameValid = true;
                }
                nicknameChecked = true;
                updateSubmitButtonState();
            },
            error: function() {
                Swal.fire({
                    icon: 'error',
                    title: '닉네임 중복 확인 중 오류가 발생했습니다.',
                    showConfirmButton: true
                });
                isNicknameValid = false;
                nicknameChecked = false;
                updateSubmitButtonState();
            }
        });
    }

    // 각 요소 확인 버튼에 이벤트 리스너 추가
    addEventListenerIfExists('.user_email_overlap_button', 'click', email_overlap_check);
    addEventListenerIfExists('.user_nickname_overlap_button', 'click', user_nickname_overlap_check);

    // 폼 제출 시 유효성 검사
    const frmMemberLogin = document.querySelector('form[name="loginModifyForm"]');
    if (frmMemberLogin) {
        frmMemberLogin.addEventListener('submit', function(e) {
            if ((!emailChecked && !nicknameChecked) || !isEmailValid && !isNicknameValid) {
                Swal.fire({
                    icon: 'error',
                    title: '중복 확인 해주세요.',
                    showConfirmButton: true
                });
                e.preventDefault();
            }
        });
    }

    // 제출 버튼 클릭 시 비활성화면 알림, 제출 막기
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

function delete_check(event, url) {
    event.preventDefault(); // 이벤트 기본 동작을 막음
    Swal.fire({
        title: '정말 탈퇴하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '예',
        cancelButtonText: '아니오'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                icon: 'success',
                title: '탈퇴되었습니다',
                showConfirmButton: true
            }).then(() => {
                // 실제 탈퇴 처리를 진행
                location.href = url;
            });
        }
    });
}
