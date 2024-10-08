$(document).ready(function() {
    $("#search-banner .searchHashtag").click(function() {
        var submenu = $(this).next("div");

        if (submenu.is(":visible")) {
            submenu.slideUp();
        } else {
            submenu.slideDown();
        }
    });
});

$(function() {
    $(".css-footer-buttons .category").click(function() {
        $(".category-info").slideToggle();
    });
});

// 검색 부분
function searchHashTags() {
    var query = document.getElementById("searchInput").value;
    fetch(`/search?query=${query}`)
        .then(response => response.json())
        .then(data => {
            var resultsDiv = document.getElementById("searchResults");
            resultsDiv.innerHTML = "";
            data.forEach(tag => {
                var tagElement = document.createElement("div");
                tagElement.textContent = tag.hashTag;
                resultsDiv.appendChild(tagElement);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: '검색 중 오류가 발생했습니다.',
                text: error.message,
                showConfirmButton: true
            });
        });
}

function submitForm() {
    var searchInput = document.getElementById('searchInput1').value;
    var searchType = searchInput.startsWith('#') ? 'hashtag' : 'word';
    document.getElementById('searchType').value = searchType;
    document.getElementById('searchForm').submit();
    return false;  // 폼이 계속 제출되지 않도록 false를 반환
}

function checkAndNavigate(event, url) {
    var searchInput = document.getElementById('searchInput1').value;
    if (searchInput.trim() === '#') {
        // 검색 입력이 비어 있을 경우, 기본 링크로 이동
        window.location.href = url;
        return false;  // 기본 동작을 막기 위해 false 반환
    }
    // 검색 입력이 있는 경우, 폼 제출
    submitForm();
    return false;
}

document.addEventListener('DOMContentLoaded', function() {
    const hashTags = document.querySelectorAll('.hash_tag');

    if (hashTags.length === 0) {
        console.error('클래스 "hash_tag"를 가진 요소를 찾을 수 없습니다.');
        return;
    }

    // 해시태그 클릭 이벤트 핸들러
    hashTags.forEach(tag => {
        tag.addEventListener('click', function () {
            const hash_tag = this.getAttribute('data-tag');
            fetch('/main/searchPopupHasTag', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ hash_tag: hash_tag })
            })
                .then(response => response.json())
                .then(data => {
                    console.log('서버 응답:', data);
                    if (data.success) {
                        displayPopups(data.popups);
                    } else {
                        Swal.fire({
                            icon: 'error',
                            text: '팝업을 조회하는 데 실패했습니다.',
                            confirmButtonText: '확인'
                        });
                    }
                })
                .catch(error => {
                    console.error('에러 발생:', error);
                    Swal.fire({
                        icon: 'error',
                        title: '에러 발생',
                        text: error.message,
                        showConfirmButton: true
                    });
                });
        });
    });

    // 페이지 로드 시 가장 인기 있는 해시태그 클릭
    if (hashTags.length > 0) {
        // 가장 첫 번째 해시태그 클릭 이벤트 트리거
        hashTags[0].click();
    }

    function displayPopups(popups) {
        const container = document.getElementById('popup-container');
        if (!container) {
            Swal.fire({
                icon: 'error',
                title: '요소를 찾을 수 없습니다.',
                text: '아이디 "popup-container"를 가진 요소를 찾을 수 없습니다.',
                showConfirmButton: true
            });
            return;
        }
        container.innerHTML = '';

        popups.forEach(popup => {
            const popupElement = document.createElement('div');
            popupElement.classList.add('popup-item');
            const imageUrl = `/popupDownload.do?popup_id=${popup.popup_id}&image_file_name=${popup.image_file_name}`;
            const viewUrl = `/popup/popupView.do?popup_id=${popup.popup_id}`;
            console.log('Generated Image URL:', imageUrl); // URL 확인

            popupElement.innerHTML = `
                <a href="${viewUrl}" id="popup-item-img">
                    <img src="${imageUrl}" alt="팝업 이미지" class="popup-image">
                    <p>${popup.title}</p>
                    <p>${popup.start_date} ~ ${popup.end_date}</p>
                    <p hidden="hidden">${popup.popup_id}</p>
                </a>
            `;
            container.appendChild(popupElement);
        });
    }
});