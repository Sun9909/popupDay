document.addEventListener('DOMContentLoaded', function () {
    const heart_icon = document.getElementById('heart-icon');
    const heart_image = document.getElementById('heart-image');

    if (!heart_icon || !heart_image) {
        console.error('heart_icon or heart_image element not found');
        return;
    }

    heart_icon.addEventListener('click', function () {
        const loginCheck = heart_icon.getAttribute('data-login-check') === 'true';
        const popup_id = heart_image.getAttribute('data-popup-id');

        if (!loginCheck) {
            alert("로그인이 필요합니다.");

            window.location.href = '/login/login.do';
            return;
        }

        fetch(`/popup/popupLike.do?popup_id=${popup_id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
        })
            .then(response => response.json())
            .then(data => {
                console.log('Response Data:', data); // 응답 데이터 확인
                if (data.success) {
                    if (data.isLiked) {
                        heart_image.src = '/images/heart_fill.svg';
                    } else {
                        heart_image.src = '/images/heart_empty.svg';
                    }
                } else {
                    alert('찜 상태를 변경하는데 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
});

function adjustWidth(input) {
    // 글자 수에 따른 너비를 ch 단위로 설정
    const length = input.value.length;
    input.style.width = (length * 13 + 13) + 'px';
}

// 페이지 로드 시, 모든 input 요소의 너비를 초기화
document.addEventListener('DOMContentLoaded', () => {
    const inputs = document.querySelectorAll('#tag');
    inputs.forEach(input => adjustWidth(input));
});

//textarea 글 크기만큼 높이주기
function adjustTextareaHeight(textarea) {
    textarea.style.height = 'auto'; // 높이 초기화
    textarea.style.height = textarea.scrollHeight + 'px'; // 내용에 맞게 높이 설정
}

document.addEventListener('DOMContentLoaded', () => {
    const textarea = document.getElementById('myTextarea');
    const textarea2 = document.getElementById('cation-content');
    adjustTextareaHeight(textarea);
    adjustTextareaHeight(textarea2);
});

function confirmUnlike(popup_id) {
    Swal.fire({
        icon: 'warning',
        title: '찜을 취소하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '확인',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            // 확인 버튼을 클릭하면 리다이렉트
            window.location.href = '/mypage/unlikeClick.do?popup_id=' + popup_id;
        }
    });
}