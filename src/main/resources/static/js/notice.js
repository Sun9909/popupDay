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
    
    //FAQ제이쿼리로 써야한다
    $(function() {
        $(".modify_button").click(function() {
            // 클릭된 버튼의 부모 요소를 선택합니다.
            let mparent = $(this).parent();

            // 부모 요소의 이전 형제 요소의 스타일을 변경합니다.
            mparent.prev().css("display", "block");

            // 부모 요소의 스타일을 변경합니다.
            mparent.css("display", "none");

            // 총 부모 요소()를 선택합니다.
            let tparent = mparent.parent();

            // tparent 내에서 closest 메서드를 사용하여 원하는 요소를 찾고 disabled 속성을 해제합니다.
            tparent.find(".answerop #faq_title").prop("disabled", false);
            tparent.find(".answer #faq_content").prop("disabled", false);
        });
    });

    // FAQ수정 반영하기
    function faq_modify(obj) {
        obj.action="/notice/modFaq.do";
        obj.submit();
    }

    //FAQ삭제 반영하기
    function fn_remove_faq(url, faq_id){
        let del_form = document.createElement("form");
        del_form.setAttribute("action", url);
        del_form.setAttribute("method","post");
        let faqNoInput = document.createElement("input");
        faqNoInput.setAttribute("type","hidden");
        faqNoInput.setAttribute("name","faq_id");
        faqNoInput.setAttribute("value", faq_id);
        del_form.appendChild(faqNoInput);
        document.body.appendChild(del_form);
        del_form.submit();
    }

    // 후기 수정하기
    function review_enable(obj) {
        document.getElementById("review-mbtn").style.display="block";
        document.getElementById("review-btn").style.display="none";
        document.getElementById("review_title").disabled=false;
        document.getElementById("review_content").disabled=false;
        let imgName=document.getElementById("id_imgFile");
        if(imgName != null) {
            imgName.disabled=false;
        } // if end
    }

    //후기 수정 반영하기
    function review_modify(obj) {
        obj.action="/notice/modReview.do";
        obj.submit();
    }

    // 후기리스트로 돌아가기
    function backToList(obj) {
        obj.action="/notice/reviewList.do";
        obj.method="post";
        obj.submit();
    }

    // 상세 보기로 전환(취소)
    function toList(obj) {
        obj.action="/notice/showReview.do"
        obj.method="post";
        obj.submit();
    }

    function fn_remove_review(){

    }


