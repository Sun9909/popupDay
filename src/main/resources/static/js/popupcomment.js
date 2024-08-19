let commentId = /*[[${comment.popup_comment_id}]]*/ '';

// 전역 변수로 currentOpenMenu 선언
let currentOpenMenu = null;

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
    // 메뉴 참조
    const menu = button.nextElementSibling;

    // 다른 메뉴가 열려 있으면 닫기
    if (currentOpenMenu && currentOpenMenu !== menu) {
        currentOpenMenu.style.display = 'none';
    }

    // 현재 메뉴를 열거나 닫음
    menu.style.display = (menu.style.display === 'block') ? 'none' : 'block';

    // 열려 있는 메뉴를 업데이트
    currentOpenMenu = (menu.style.display === 'block') ? menu : null;

    // 스크롤 제어
    if (currentOpenMenu) {
        document.body.style.overflow = 'hidden'; // 스크롤 비활성화
    } else {
        document.body.style.overflow = 'auto';   // 스크롤 활성화
    }
}

// 메뉴 외부를 클릭하면 메뉴를 닫음
document.addEventListener('click', function(event) {
    const isClickInside = currentOpenMenu && currentOpenMenu.contains(event.target);
    const isButtonClick = event.target.classList.contains('review-options-button');

    // 메뉴 외부를 클릭하거나 메뉴 버튼을 다시 클릭하면 닫기
    if (!isClickInside && !isButtonClick) {
        if (currentOpenMenu) {
            currentOpenMenu.style.display = 'none';
            currentOpenMenu = null;
            document.body.style.overflow = 'auto'; // 스크롤 다시 활성화
        }
    }
});

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
function openEditPopup(button) {
    // data-* 속성에서 값을 가져오기
    const commentId = button.getAttribute('data-comment-id');
    const content = button.getAttribute('data-content');
    const rating = button.getAttribute('data-rating');

    // popup_comment_id를 숨겨진 필드에 설정
    document.getElementById('popup_comment_id').value = commentId;

    // 팝업에 텍스트 영역에 기존 내용을 설정
    document.getElementById('contentUpdate').value = content;

    // 팝업에서 기존 별점을 설정
    if (rating) {
        const ratingInput = document.querySelector(`input[name="rating"][value="${rating}"]`);
        if (ratingInput) {
            ratingInput.checked = true;
        } else {
            console.error(`Rating input with value ${rating} not found.`);
        }
    }

    // 팝업을 표시
    document.getElementById("editPopup").style.display = "block";
}

function closeEditPopup() {
    // 레이어 팝업을 숨깁니다.
    document.getElementById("editPopup").style.display = "none";
}

//내용, 별점 작성 하지 않았을 경우 alert
function commentForm() {
    const rating = document.querySelector('input[name="rating"]:checked');
    if (!rating) {
        Swal.fire({
            icon: 'warning',
            title: '별점을 선택해주세요.',
            showConfirmButton: true
        });
        return false;  // 폼 제출을 막음
    }

    const content = document.getElementById('content').value.trim();
    if (content === '') {
        Swal.fire({
            icon: 'warning',
            title: '내용을 작성해주세요.',
            showConfirmButton: true
        });
        return false;  // 폼 제출을 막음
    }

    return true; // 모든 조건이 충족되면 폼 제출 허용
}

function commentEditForm() {
    const rating = document.querySelector('input[name="rating"]:checked');
    if (!rating) {
        Swal.fire({
            icon: 'warning',
            title: '별점을 선택해주세요.',
            showConfirmButton: true
        });
        return false;  // 폼 제출을 막음
    }

    const content = document.getElementById('contentUpdate').value.trim();
    if (content === '') {
        Swal.fire({
            icon: 'warning',
            title: '내용을 작성해주세요.',
            showConfirmButton: true
        });
        return false;  // 폼 제출을 막음
    }

    return true; // 모든 조건이 충족되면 폼 제출 허용
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

