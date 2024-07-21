function toggleLike(imgElement) {
    const popupId = imgElement.getAttribute('data-popup-id'); // data-popup-id에서 팝업 ID를 가져옵니다.

    fetch('/popup/toggleLike.do?popup_id=' + popup_id, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.text())
        .then(message => {
            alert(message);
            checkIfLiked(popupId, imgElement); // 찜 상태 업데이트
        }).catch(error => {
        alert('오류 발생: ' + error);
    });
}

function checkIfLiked(popup_id, imgElement) {
    fetch('/popup/isLiked.do?popup_id=' + popup_id)
        .then(response => response.json())
        .then(isLiked => {
            if (isLiked) {
                imgElement.src = '/images/heart_fill.svg'; // 찜된 상태의 이미지
            } else {
                imgElement.src = '/images/heart_empty.svg'; // 찜되지 않은 상태의 이미지
            }
        });
}

// 페이지 로드 시 찜 상태 확인
window.onload = function() {
    document.querySelectorAll('.heart').forEach(img => {
        const popup_id = img.getAttribute('data-popup-id');
        checkIfLiked(popup_id, img);
    });
};