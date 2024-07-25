$(document).ready(function(){
    $("#search-banner .searchHashtag").click(function() {
        var submenu = $(this).next("div");

        if(submenu.is(":visible")) {
            submenu.slideUp();
        }else{
            submenu.slideDown();
        }
    });
});

$(function(){
    $(".css-footer-buttons .category").click(function() {
        $(".category-info").slideToggle();
    });
});

//검색부분
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
        .catch(error => console.error('Error:', error));
}

//search타입이 hashtag인지 word인지 구분하는 js
function submitForm() {
    var searchInput = document.getElementById('searchInput1').value;
    var searchType = searchInput.startsWith('#') ? 'hashtag' : 'word';
    document.getElementById('searchType').value = searchType;
    document.getElementById('searchForm').submit();
    return false;  // 폼이 계속 제출되지 않도록 false를 반환
}


// 해시해시해시해시태그
document.addEventListener('DOMContentLoaded', function () {
    const hashTags = document.querySelectorAll('.hash_tag');

    if (hashTags.length === 0) {
        console.error('클래스 "hash_tag"를 가진 요소를 찾을 수 없습니다.');
        return;
    }

    hashTags.forEach(tag => {
        tag.addEventListener('click', function () {
            const hash_tag = this.getAttribute('data-tag');
            fetch(`/main/searchPopupHasTag?hash_tag=${hash_tag}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
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
                .catch(error => {
                    console.error('에러 발생:', error);
                });
        });
    });

    function displayPopups(popups) {
        const container = document.getElementById('popup-container');
        if (!container) {
            console.error('아이디 "popup-container"를 가진 요소를 찾을 수 없습니다.');
            return;
        }
        container.innerHTML = '';

        popups.forEach(popup => {
            const popupElement = document.createElement('div');
            popupElement.classList.add('popup-item');
            popupElement.innerHTML = `
                <img src="${popup.image_file_name}" alt="팝업 이미지" class="popup-image">
                <p>${popup.title}</p>
            `;
            container.appendChild(popupElement);
        });
    }
});
