document.addEventListener('DOMContentLoaded', function () {
    const heart_icon = document.getElementById('heart-icon');
    const popup_id = heart_icon.dataset.popup_id;
    const heartEmptySrc = heart_icon.dataset.heartEmpty;
    const heartFilledSrc = heart_icon.dataset.heartFilled;

    if (!popup_id) {
        console.error('팝업 정보가 올바르지 않습니다. 다시 시도해 주세요.');
        return;
    }

    heart_icon.addEventListener('click', function () {
        // 로그인 상태 체크
        fetch('/popup/checkLoginStatus.do')
            .then(response => response.json())
            .then(data => {
                if (data.isLoggedIn) {
                    toggleFavorite(popup_id);
                } else {
                    // 로그인하지 않은 경우 로그인 페이지로 리디렉션
                    window.location.href = '/login';
                }
            })
            .catch(error => console.error('Error:', error));
    });

    function toggleFavorite(popup_id) {
        const isLike = heart_icon.src.includes(heartFilledSrc); // 현재 상태 확인
        const url = isLike ? '/popup/removeLike.do' : '/popup/addLike.do';

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ popupId: popup_id })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    heart_icon.src = isLike ? heartEmptySrc : heartFilledSrc;
                } else {
                    alert('찜 상태 업데이트 중 오류가 발생했습니다.');
                }
            })
            .catch(error => console.error('Error:', error));
    }
});