document.addEventListener('DOMContentLoaded', function() {
    let isUserIdValid = false;
    let isNicknameValid = false;

    //제출 버튼 상태 업데이트 함수 - 모두 유효할 때만 제출 버튼 활성화
    function updateSubmitButtonState() {
        let submitButtons = document.querySelectorAll('input[type="submit"]');
        submitButtons.forEach(function(submitButton) {
            submitButton.disabled = !(isUserIdValid && isNicknameValid);
        });
        console.log('Submit button state updated:', isUserIdValid, isNicknameValid);
    }

    //제출 버튼 비활성화 시 알림 함수
    function showAlertIfSubmitDisabled() {
        if (!isUserIdValid || !isNicknameValid) {
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

    //아이디 중복 확인
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

    //닉네임 중복 확인
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

    //각 요소 확인 버튼에 이벤트 리스너 추가
    addEventListenerIfExists('.id_overlap_button', 'click', id_overlap_check);
    addEventListenerIfExists('.user_nikname_overlap_button', 'click', user_nikname_overlap_check);

    //폼 제출 시 유효성 검사(안되는가봄)
    const frmMemberLogin = document.querySelector('form[name="memberModifyForm"]');
    if (frmMemberLogin) {
        frmMemberLogin.addEventListener('submit', function(e) {
            if (!isUserIdValid || !isNicknameValid) {
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

// 아이디 중복 확인 함수
// function id_overlap_check() {
//     var user_id_input = document.querySelector('input[name="user_id"]'); // 'user_id' input 요소를 선택
//     var original_user_id = user_id_input.getAttribute('data-original-value');
//
//     // 아이디 입력 값이 비어있는지 확인
//     if (user_id_input.value === '') {
//         alert('아이디를 입력해주세요.');
//         return Promise.resolve(false);
//     }
//
//     // 기존 아이디와 입력 값 비교
//     if (user_id_input.value === original_user_id) {
//         alert("기존 아이디와 동일합니다");
//         return Promise.resolve(false);
//     }
//
//     // AJAX 요청을 통해 서버에 아이디 중복 확인 요청
//     return new Promise((resolve, reject) => {
//         $.ajax({
//             url: "/admin/check-id", // 아이디 중복 확인을 위한 서버 URL
//             data: {
//                 'user_id': user_id_input.value // 입력된 아이디 값
//             },
//             datatype: 'json', // 응답 데이터 타입
//             success: function (data) {
//                 // 서버 응답이 중복 아이디 존재 여부를 나타냄
//                 if (data) {
//                     alert("이미 존재하는 아이디 입니다."); // 중복 아이디인 경우 경고 메시지
//                     user_id_input.focus(); // 아이디 입력란에 포커스
//                     resolve(false);
//                 } else {
//                     alert("사용가능한 아이디 입니다."); // 중복되지 않은 경우 메시지
//                     resolve(true);
//                 }
//             },
//             error: function (error) {
//                 console.error("Error:", error); // 오류 발생 시 콘솔에 오류 출력
//                 alert("아이디 중복 확인 중 오류가 발생했습니다."); // 사용자에게 오류 메시지 표시
//                 reject(false);
//             }
//         });
//     });
// }

// function modify_check() {
//     if (confirm("회원 정보를 수정하시겠습니까?")) {
//         return Promise.all([id_overlap_check(), user_nikname_overlap_check()])
//             .then(results => {
//                 if (results.every(result => result)) {
//                     alert("수정되었습니다");
//                     return true;
//                 } else {
//                     alert("중복된 정보가 있습니다. 다시 수정해주세요.");
//                     return false;
//                 }
//             })
//             .catch(error => {
//                 console.error("Error during validation:", error);
//                 alert("검증 중 오류가 발생했습니다. 다시 시도해주세요.");
//                 return false;
//             });
//     } else {
//         alert("다시 수정해주세요");
//         return false;
//     }
// }

function delete_check() {
    if (confirm("회원을 삭제하시겠습니까?") == true) {
        alert("삭제되었습니다");
        return true;
    }
    else {
        return false;
    }
}
