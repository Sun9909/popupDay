$(document).ready(function(){
    $("#search-banner .search").click(function() {
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
function search() {
    const query = document.getElementById('searchInput').value;
    window.location.href = `/search?query=${encodeURIComponent(query)}`;
}