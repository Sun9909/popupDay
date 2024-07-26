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

// search 타입이 hashtag인지 word인지 구분하는 js
function submitForm() {
    var searchInput = document.getElementById('searchInput1').value;
    var searchType = searchInput.startsWith('#') ? 'hashtag' : 'word';
    document.getElementById('searchType').value = searchType;
    document.getElementById('searchForm').submit();
    return false;  // 폼이 계속 제출되지 않도록 false를 반환
}

// 해시태그 처리
document.addEventListener('DOMContentLoaded', function() {
    const hashTags = document.querySelectorAll('.hash_tag');

    if (hashTags.length === 0) {
        console.error('클래스 "hash_tag"를 가진 요소를 찾을 수 없습니다.');
        return;
    }

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
                        alert('팝업을 조회하는 데 실패했습니다.');
                    }
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log('서버 응답:', data);
                        if (data.success) {
                            displayPopups(data.popups);
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: '팝업 조회 실패',
                                text: '팝업을 조회하는 데 실패했습니다.',
                                showConfirmButton: true
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
            console.log('Generated Image URL:', imageUrl); // URL 확인

            popupElement.innerHTML = `
            <img src="${imageUrl}" alt="팝업 이미지" class="popup-image">
            <p>${popup.title}</p>
            <p>${popup.start_date} ~ ${popup.end_date}</p>
            <p>${popup.address}</p>
            <p hidden="hidden">${popup.popup_id}</p>
        `;
            container.appendChild(popupElement);
        });
    }
});