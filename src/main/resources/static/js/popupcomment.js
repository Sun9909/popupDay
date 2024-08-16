// 텍스트 영역 글자 수 업데이트 기능
function updateCharacterCount() {
    const textarea = document.getElementById('content');
    const charCount = document.getElementById('charCount');
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


// 수정된 리뷰 저장 기능
function saveReview(updateId) {
    const editTextarea = document.getElementById(`edit-review-${updateId}`);
    const updatedContent = editTextarea.value.trim();

    fetch(`/popupComment/update`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            updateId: updateId,
            content: updatedContent
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                document.getElementById(`review-content-${updateId}`).textContent = updatedContent;
                document.getElementById(`review-content-${updateId}`).style.display = 'block';
                editTextarea.style.display = 'none';
                document.getElementById(`save-review-${updateId}`).style.display = 'none';
            } else {
                alert('리뷰 수정에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('리뷰 수정에 실패했습니다.');
        });
}

