document.addEventListener('DOMContentLoaded', function() {
    let isUserIdValid = false;
    let isNicknameValid = false;
    let userIdChecked = false;
    let nicknameChecked = false;

    // 제출 버튼 상태 업데이트 함수 - 아이디 또는 닉네임 중 하나라도 유효할 때 제출 버튼 활성화
    function updateSubmitButtonState() {
        let submitButtons = document.querySelectorAll('input[type="submit"]');
        submitButtons.forEach(function(submitButton) {
            submitButton.disabled = !(isUserIdValid || isNicknameValid);
        });
        console.log('Submit button state updated:', isUserIdValid, isNicknameValid);
    }

    // 제출 버튼 비활성화 시 알림 함수
    function showAlertIfSubmitDisabled() {
        if (!isUserIdValid && !isNicknameValid) {
            Swal.fire({
                icon: 'info',
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

    // 아이디 중복 확인
    function id_overlap_check() {
        let userId = document.querySelector('input[name="user_id"]').value;
        let origin_id  = document.querySelector('input[name="origin_id"]').value;
        if (userId === '') {
            Swal.fire({
                icon: 'info',
                title: '아이디를 입력해주세요.',
                showConfirmButton: true
            });
            isUserIdValid = false;
            userIdChecked = false;
            updateSubmitButtonState();
            return;
        }
        else if (userId === origin_id) {
            Swal.fire({
                icon: 'info',
                title: '기존 아이디와 동일합니다.',
                showConfirmButton: true
            });
            isUserIdValid = true;
            userIdChecked = true;
            updateSubmitButtonState();
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
                        title: '이미 존재하는 아이디 입니다.',
                        showConfirmButton: true
                    });
                    isUserIdValid = false;
                } else {
                    Swal.fire({
                        icon: 'success',
                        title: '사용가능한 아이디 입니다.',
                        showConfirmButton: true
                    });
                    isUserIdValid = true;
                }
                userIdChecked = true;
                updateSubmitButtonState();
            },
            error: function() {
                Swal.fire({
                    icon: 'error',
                    title: '아이디 중복 확인 중 오류가 발생했습니다.',
                    showConfirmButton: true
                });
                isUserIdValid = false;
                userIdChecked = false;
                updateSubmitButtonState();
            }
        });
    }

    // 닉네임 중복 확인
    function user_nickname_overlap_check() {
        let nickname = document.querySelector('input[name="user_nickname"]').value;
        let origin_nickname  = document.querySelector('input[name="origin_nickname"]').value;
        if (nickname === '') {
            Swal.fire({
                icon: 'info',
                title: '닉네임을 입력해주세요.',
                showConfirmButton: true
            });
            isNicknameValid = false;
            nicknameChecked = false;
            updateSubmitButtonState();
            return;
        } else if (nickname === origin_nickname) {
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
            url: "/login/check-nickname",
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
    addEventListenerIfExists('.id_overlap_button', 'click', id_overlap_check);
    addEventListenerIfExists('.user_nickname_overlap_button', 'click', user_nickname_overlap_check);

    // 폼 제출 시 유효성 검사
    const frmMemberLogin = document.querySelector('form[name="memberModifyForm"]');
    if (frmMemberLogin) {
        frmMemberLogin.addEventListener('submit', function(e) {
            if ((!userIdChecked && !nicknameChecked) || (!isUserIdValid && !isNicknameValid)) {
                Swal.fire({
                    icon: 'info',
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

function delete_check() {
    Swal.fire({
        title: '회원을 삭제하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '삭제',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: '삭제되었습니다',
                icon: 'success',
                showConfirmButton: true
            });
            return true;
        } else {
            return false;
        }
    });
}
