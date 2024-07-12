    function openTab(tabId) {
    // 모든 탭의 내용을 비활성화
    var contents = document.getElementsByClassName('content');
    for (var i = 0; i < contents.length; i++) {
    contents[i].classList.remove('active');
}

    // 클릭한 탭의 내용을 활성화
    var activeTab = document.getElementById(tabId);
    activeTab.classList.add('active');

    // 모든 탭 버튼을 비활성화
    var tabs = document.getElementsByClassName('contact');
    for (var j = 0; j < tabs.length; j++) {
    tabs[j].classList.remove('active');
}

    // 클릭한 탭 버튼을 활성화
    var activeTabButton = document.querySelector('[onclick="openTab(\'' + tabId + '\')"]');
    activeTabButton.classList.add('active');
}

    function switchReviewTab(tabClass) {
    // 모든 리뷰 탭의 내용을 비활성화
    var reviewContents = document.getElementsByClassName('review-content');
    for (var i = 0; i < reviewContents.length; i++) {
    reviewContents[i].classList.remove('active');
}

    // 클릭한 리뷰 탭의 내용을 활성화
    var activeReviewTab = document.querySelector('.' + tabClass);
    activeReviewTab.classList.add('active');

    // 모든 리뷰 탭 버튼을 비활성화
    var reviewTabs = document.getElementsByClassName('custom-button');
    for (var j = 0; j < reviewTabs.length; j++) {
    reviewTabs[j].classList.remove('active');
}

    // 클릭한 리뷰 탭 버튼을 활성화
    var activeReviewTabButton = document.querySelector('[onclick="switchReviewTab(\'' + tabClass + '\')"]');
    activeReviewTabButton.classList.add('active');
}

//     //토글을 이용하여 게시글 보기
//     $(function () {
//     let sw=[false,false,false];
//     let index;
//     $('.answerop').click(function() {
//     index=$(this).parent().index();
//     //alert(index);
//     sw[index]=!sw[index];
//     if(sw[index]) {
//         $(this).parent().find('.answer').css('display','block');
//         $('#toggle'+index).attr('src','images/angle-up-solid.svg');
//     }else {
//         $(this).parent().find('.answer').css('display','none');
//         $('#toggle'+index).attr('src','images/angle-down-solid.svg');
//     }
// });
// });

    // 여러개 이미지 추가 함수
    let count=1;
    function fn_addFile() { // cnt가 1이면 이미지 1개
        $('#dock_file').append('<input type="file" name="imgFile' + count + '"><br>');
        count++;
    }

    //FAQ 토글
    $(function(){
        $(".answerop").click(function() {
            $(this).closest(".faqitem").find(".answer").slideToggle();

            // 이미지도 토글
            var img = $(this).closest('.answerop').find('#toggle');
            if (img.attr('src') === '/images/angle-up-solid.svg') {
                img.attr('src', '/images/angle-down-solid.svg');
            } else {
                img.attr('src', '/images/angle-up-solid.svg');
            }
        });
    });


