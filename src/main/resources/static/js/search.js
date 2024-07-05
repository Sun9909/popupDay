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