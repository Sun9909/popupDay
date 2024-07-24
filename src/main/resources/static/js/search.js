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
}

//달력으로 서치 js

    // 달력 부분 추가
    const dateCellsContainer = document.querySelector('.tbody');
    const selectedDateInput = document.getElementById('selectedDate');
    let selectedDate = null;

    // 예시로 7월 달력 날짜 셀을 동적으로 생성
    for (let i = 1; i <= 31; i++) {
        const dateCell = document.createElement('td');
        dateCell.className = 'date-cell';
        const date = `2024-07-${String(i).padStart(2, '0')}`;
        dateCell.setAttribute('data-date', date);
        dateCell.textContent = i;
        dateCellsContainer.appendChild(dateCell);

        // 날짜 셀 클릭 이벤트 처리
        dateCell.addEventListener('click', function() {
            selectedDate = this.getAttribute('data-date');
            selectedDateInput.value = selectedDate;

            // 이전에 선택된 날짜 셀에서 선택 클래스 제거
            const previouslySelected = document.querySelector('.date-cell.selected-date');
            if (previouslySelected) {
                previouslySelected.classList.remove('selected-date');
            }

            // 현재 선택된 날짜 셀에 선택 클래스 추가
            this.classList.add('selected-date');
        });
    }

    // 팝업 조회 버튼 클릭 이벤트 처리
    const popupButton = document.getElementById('add-button');
    if (popupButton) {
        popupButton.addEventListener('click', function() {
            if (!selectedDate) {
                alert("날짜를 선택해주세요.");
            } else {
                // 폼을 제출하여 백엔드로 선택된 날짜를 전송
                document.getElementById('form').submit();
            }
        });
    }
