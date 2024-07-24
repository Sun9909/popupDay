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
