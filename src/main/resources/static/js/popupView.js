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