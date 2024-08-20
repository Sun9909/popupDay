let commentId = /*[[${comment.popup_comment_id}]]*/ '';

// 전역 변수로 currentOpenMenu 선언
let isPopupVisible = false;
let currentOpenMenu = null;

// 텍스트 영역 글자 수 업데이트 기능
function updateCharacterCount() {
    const textarea = document.getElementById('content');
    const charCount = document.getElementById('charCount');
    const maxLength = 50;
    const currentLength = textarea.value.length;

    // 글자 수가 최대치를 초과하지 않도록 제한
    if (currentLength > maxLength) {
        textarea.value = textarea.value.substring(0, maxLength);
    }

    charCount.textContent = `(${currentLength} / ${maxLength})`;
}

// 텍스트 영역 글자 수 업데이트 기능(레이어 팝업 수정부분)
function updateCharacterCount2() {
    const textarea = document.getElementById('contentUpdate');
    const charCount = document.getElementById('charCount2');
    const maxLength = 50;
    const currentLength = textarea.value.length;

    // 글자 수가 최대치를 초과하지 않도록 제한
    if (currentLength > maxLength) {
        textarea.value = textarea.value.substring(0, maxLength);
    }

    charCount.textContent = `(${currentLength} / ${maxLength})`;
}

// 텍스트 입력 시 글자 수 업데이트 이벤트 리스너 추가
document.getElementById('content').addEventListener('input', updateCharacterCount);
document.getElementById('contentUpdate').addEventListener('input', updateCharacterCount2);

//메뉴가 열려있을 때 레이어팝업이 떠 있는지 확인하는 함수
function isPopupOpen() {
    return document.getElementById("editPopup").style.display === "block";
}

function toggleOptionsMenu(button) {
    const menu = button.nextElementSibling;

    if (currentOpenMenu && currentOpenMenu !== menu) {
        currentOpenMenu.style.display = 'none';
    }

    menu.style.display = (menu.style.display === 'block') ? 'none' : 'block';
    currentOpenMenu = (menu.style.display === 'block') ? menu : null;

    // 스크롤 제어 - 메뉴 또는 팝업이 열려 있는 경우 스크롤 비활성화
    if (currentOpenMenu || isPopupVisible) {
        document.body.style.overflow = 'hidden';
    } else {
        document.body.style.overflow = 'auto';
    }
}

document.addEventListener('click', function(event) {
    const isClickInside = currentOpenMenu && currentOpenMenu.contains(event.target);
    const isButtonClick = event.target.classList.contains('review-options-button');

    if (!isClickInside && !isButtonClick) {
        if (currentOpenMenu) {
            currentOpenMenu.style.display = 'none';
            currentOpenMenu = null;

            // 스크롤 제어 - 팝업이 열려 있지 않은 경우 스크롤을 활성화
            if (!isPopupVisible) {
                document.body.style.overflow = 'auto'; // 스크롤 활성화
            }
        }
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

    // 글자 수를 업데이트
    const maxLength = 50;
    const currentLength = content.length;

    // 글자 수 표시 업데이트
    const charCount = document.getElementById('charCount2');
    charCount.textContent = `(${currentLength} / ${maxLength})`;

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

    document.body.style.overflow = 'hidden'; // 스크롤 비활성화

    isPopupVisible = true; // 팝업이 열려 있음을 기록
}

//클릭 이벤트를 추가로 처리해서 팝업이 열려 있을 때 스크롤이 활성화되지 않도록 하는 함수
document.addEventListener('click', function(event) {
    const isClickInside = currentOpenMenu && currentOpenMenu.contains(event.target);
    const isButtonClick = event.target.classList.contains('review-options-button');
    const isEditPopupOpen = isPopupOpen();

    if (!isClickInside && !isButtonClick) {
        if (currentOpenMenu) {
            currentOpenMenu.style.display = 'none';
            currentOpenMenu = null;

            // 스크롤 제어 - 수정 팝업이 열려있는 경우 스크롤을 비활성화 유지
            if (!isEditPopupOpen) {
                document.body.style.overflow = 'auto'; // 스크롤 활성화
            }
        }
    }
});

function closeEditPopup() {
    document.getElementById("editPopup").style.display = "none";

    isPopupVisible = false; // 팝업이 닫혔음을 기록

    // 메뉴가 열려 있지 않다면 스크롤을 활성화
    if (!currentOpenMenu) {
        document.body.style.overflow = 'auto'; // 스크롤 활성화
    }
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

function commentEditForm2() {
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

