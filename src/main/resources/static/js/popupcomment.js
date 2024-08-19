let commentId = /*[[${comment.popup_comment_id}]]*/ '';

// 텍스트 영역 글자 수 업데이트 기능
function updateCharacterCount() {
    const textarea = document.getElementById('content');
    const charCount = document.getElementById('charCount');
    const remaining = 50 - textarea.value.length;
    charCount.textContent = `(${remaining} / 50)`;
}
// 텍스트 영역 글자 수 업데이트 기능(레이어 팝업 수정부분)
function updateCharacterCount2() {
    const textarea = document.getElementById('contentUpdate');
    const charCount = document.getElementById('charCount2');
    const remaining = 50 - textarea.value.length;
    charCount.textContent = `(${remaining} / 50)`;
}

// 메뉴 토글 함수: 수정/삭제 메뉴 표시 토글
function toggleOptionsMenu(button) {
    const menu = button.nextElementSibling;
    menu.style.display = (menu.style.display === 'none' || menu.style.display === '') ? 'block' : 'none';
}

// 전역 클릭 이벤트 리스너: 메뉴 외부 클릭 시 닫기
document.addEventListener('click', function (event) {
    const isClickInside = event.target.closest('.review-options');
    if (!isClickInside) {
        document.querySelectorAll('.review-options-menu').forEach(menu => {
            menu.style.display = 'none';
        });
    }
});

// 팝업 열기
function openEditPopup() {
    // 레이어 팝업을 표시합니다.
    document.getElementById("editPopup").style.display = "block";
}

function closeEditPopup() {
    // 레이어 팝업을 숨깁니다.
    document.getElementById("editPopup").style.display = "none";
}



// 페이지 로드 시 글자 수를 초기화합니다.
document.addEventListener('DOMContentLoaded', updateCharacterCount);

function validateForm() {
    const textarea = document.getElementById('content');
    const errorMessage = document.getElementById('errorMessage');
    const trimmedValue = textarea.value.trim();

    if (trimmedValue === '') {
        errorMessage.textContent = '댓글을 입력해주세요. 공백만 입력할 수 없습니다.';
        return false; // 폼 제출을 막습니다.
    }

    errorMessage.textContent = ''; // 에러 메시지를 초기화합니다.
    return true; // 폼 제출을 허용합니다.
}

// 폼 제출 시 유효성 검사를 수행하도록 설정합니다.
document.querySelector('form').addEventListener('submit', function(event) {
    if (!validateForm()) {
        event.preventDefault(); // 폼 제출을 막습니다.
    }
});

