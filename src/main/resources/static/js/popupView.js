document.addEventListener('DOMContentLoaded', function () { // 홈페이지 로딩 후 실행
    const heart_icon = document.getElementById('heart-icon');
    const heart_image = document.getElementById('heart-image');

    heart_icon.addEventListener('click', function () {
        const loginCheck = heart_icon.getAttribute('data-login-check') === 'true'; // 로그인 여부 확인
        const popup_id = heart_image.getAttribute('data-popup-id'); // 팝업 id

        if (!loginCheck) {
            // 로그인 페이지로 리디렉션
            alert("loginCheck = " + loginCheck);
            window.location.href = '/login/login.do';
            return;
        }

        // AJAX 요청으로 찜 상태를 토글
        fetch(`/popup/popupLike?popup_id=${popup_id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // 찜 상태에 따라 이미지 변경
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