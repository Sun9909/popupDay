
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
        tparent.find(".answerop #faq_title").css("pointer-events", "auto");
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
    let imgName=document.querySelectorAll("#id_imgFiles");
    let imgBtn = document.querySelectorAll("#notice-modify-img");
    if(imgName != null) {
        for(let i=0; i<imgName.length; i++) {
            imgBtn[i].style.display = "inline-block";
            imgName[i].disabled=false;
        }
    } // if end
}

//후기 수정 반영하기
function review_modify(obj) {
    obj.action="/notice/modReview.do";
    obj.submit();
}

//후기삭제 반영하기
function fn_remove_review(url, review_id){
    let del_form = document.createElement("form");
    del_form.setAttribute("action", url);
    del_form.setAttribute("method","post");
    let reviewNoInput = document.createElement("input");
    reviewNoInput.setAttribute("type","hidden");
    reviewNoInput.setAttribute("name","review_id");
    reviewNoInput.setAttribute("value", review_id);
    del_form.appendChild(reviewNoInput);
    document.body.appendChild(del_form);
    del_form.submit();
}

// 후기리스트로 돌아가기
function backToFaq(obj) {
    obj.action="/notice/reviewList.do";
    obj.method="post";
    obj.submit();
}

// 상세 보기로 전환(취소)
function toList(obj) {
    obj.action="/notice/viewReview.do"
    obj.method="post";
    obj.submit();
}

// 이미지 미리보기 구현
function readImage(input, num) {
    if(input.files && input.files[0]) { // 현재 input 객체 정보 (이미지 선택시)
        let reader=new FileReader();
        reader.onload=function (event) {
            console.log(event);
            $("#preview" + num).attr("src", event.target.result);
        }
        reader.readAsDataURL(input.files[0]); // 이미지 처리
    }else { // 이미지 미선택 (취소시 빈값으로 변경)
        $("#preview").attr("src","#");
    } // if end
} // readImage end


/*************************************************************************/
/* 공지사항 상세보기 js*/

// 공지사항 수정 반영하기
function notice_modify(obj) {
    obj.action="/notice/modNotice.do";
    obj.submit();
}

// 상세 보기로 전환(취소)
function notice_toList(obj) {
    obj.action="/notice/noticeView.do"
    obj.method="post";
    obj.submit();
}

//공지사항 삭제 반영하기
function fn_remove_notice(url, notice_id){
    if(confirm("공지사항을 삭제하시겠습니까?")){
        let dele_form = document.createElement("form");
        dele_form.setAttribute("action", url);
        dele_form.setAttribute("method","post");
        let noticeNoInput = document.createElement("input");
        noticeNoInput.setAttribute("type","hidden");
        noticeNoInput.setAttribute("name","notice_id");
        noticeNoInput.setAttribute("value", notice_id);
        dele_form.appendChild(noticeNoInput);
        document.body.appendChild(dele_form);
        dele_form.submit();
    }

}

//공지사항 수정하기
function notice_enable(obj) {
    document.getElementById("notice-mbtn").style.display="block";
    document.getElementById("notice-btn").style.display="none";
    document.getElementById("title").disabled=false;
    document.getElementById("content").disabled=false;
    let noimgName=document.querySelectorAll("#id_imgFiles");
    let noimgBtn = document.querySelectorAll("#id_imgFiles");
    if(noimgName != null) {
        for(let i=0; i<noimgName.length; i++) {
            noimgBtn[i].style.display = "inline-block";
            noimgName[i].disabled=false;
        }
    } // if end
}


// 이미지 미리보기 구현
function Imageread_notice(input, num) {
    if(input.files && input.files[0]) { // 현재 input 객체 정보 (이미지 선택시)
        console.log("파일이 선택되었습니다:", input.files[0]);
        let reader=new FileReader();
        reader.onload=function (event) {
            console.log("이미지로드완료:",event);
            $("#preview2" + num).attr("src", event.target.result);
        }
        reader.readAsDataURL(input.files[0]); // 이미지 처리
    }else { // 이미지 미선택 (취소시 빈값으로 변경)
        console.log("이미지가 선택되지 않았습니다.");
        $("#preview2").attr("src","#");
    } // if end
} // readImage end


/*********************** Q&AQ&AQ&A *****************************/
function toggleAnswerQna() {
    var answerQnaDiv = document.getElementById('answer_qna_div');
    if (answerQnaDiv.style.display === 'none') {
        answerQnaDiv.style.display = 'block'; // 보이기
    } else {
        answerQnaDiv.style.display = 'none';  // 숨기기
    }
}


//qna 수정하기
function qna_enable(obj) {
    document.getElementById("qna-mbtn").style.display = "block"; //수정반영하기와수정취소하기
    document.getElementById("qna-btn").style.display = "none";  // 수정하기와 사젝하기 버트 숨기긱
    document.getElementById("title").disabled = false;  //제목 입력 필드 활상화
    document.getElementById("content").disabled = false; // 내용 입력 필드 활성화
}

// qna수정 반영하기
function qna_modify(obj) {
    obj.action="/notice/modQna.do";
    obj.submit();

}

// //qna 삭제 반영하기
function fn_remove_qna(url, qna_id) {
    Swal.fire({
        title: 'Q&A 질문을 삭제하시겠습니까?',
        text: "이 작업은 되돌릴 수 없습니다!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            let dele_form = document.createElement("form");
            dele_form.setAttribute("action", url);
            dele_form.setAttribute("method", "post");
            let qnaInput = document.createElement("input");
            qnaInput.setAttribute("type", "hidden");
            qnaInput.setAttribute("name", "qna_id");
            qnaInput.setAttribute("value", qna_id);
            dele_form.appendChild(qnaInput);
            document.body.appendChild(dele_form);
            dele_form.submit();
        }
    });
}
// 답변 수정하기
function answer_enable(form) {
    // 텍스트 영역 활성화
    const textarea = form.querySelector('textarea[name="answer"]');
    textarea.removeAttribute('disabled');

    // 버튼 상태 변경
    document.getElementById('answer-btn').style.display = 'none';
    document.getElementById('answer-mbtn').style.display = 'block';
}
// 답변 저장하기
function submitAnswer(form, actionUrl) {
    // 폼 액션 설정
    form.action = actionUrl; // 동적으로 URL 설정
    form.submit();
}

// 답변 삭제하기
function fn_remove_answer(url, qna_id) {
    Swal.fire({
        title: '답변을 삭제하시겠습니까?',
        text: "이 작업은 되돌릴 수 없습니다!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    'qna_id': qna_id
                }).toString()
            })
                .then(response => {
                    if (response.redirected) {
                        // Handle the redirection
                        window.location.href = response.url;
                    } else if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Network response was not ok.');
                    }
                })
                .then(data => {
                    if (data && data.success) {
                        document.getElementById('answer-section').style.display = 'none';
                        document.getElementById('user-answer-section').style.display = 'block';
                        Swal.fire('삭제 완료', '답변이 성공적으로 삭제되었습니다.', 'success').then(() => {
                            window.location.href = '/notice/qnaList.do';
                        });
                    } else {
                        Swal.fire('삭제 실패', '답변 삭제에 실패했습니다.', 'error');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire('삭제 실패', '오류가 발생했습니다.', 'error');
                });
        }
    });
}



