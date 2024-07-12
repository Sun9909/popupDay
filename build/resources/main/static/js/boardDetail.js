$(function () {
    // 배너 이미지 순환
    const images = $('.banner-img');
    let currentIndex = 0; // 현재 보여지고 있는 이미지의 인덱스

    // 초기에 첫 번째 이미지만 보이도록 설정
    images.hide().first().show();

    images.on('click', function() {
        currentIndex = (currentIndex + 1) % images.length; // 다음 이미지의 인덱스 계산
        images.hide().eq(currentIndex).show(); // 다음 이미지 보여주기
    });
    function updateImage() {
        images.forEach(function(img, index) {
            if (index === currentIndex) {
                img.style.display = 'block'; // 현재 보여질 이미지
            } else {
                img.style.display = 'none'; // 나머지 이미지 숨김
            }
        });
    }
});

    // 토글을 이용하여 게시글 보기
    $(function () {
    let sw = [false,false,false,false,false]; // toggle 상태를 저장할 배열
    let index
    // 각 토글 이미지를 클릭했을 때 toggle 이벤트 처리
    $('.popupdetail-total').click(function() {
       index=$(this).parent().index();
        sw[index]=!sw[index];
        if(sw[index]) {
            $(this).parent().find('.answer').css('display','block');
            $('#toggle'+index).attr('src','images/angle-up-solid.svg');
        }else {
            $(this).parent().find('.answer').css('display','none');
            $('#toggle'+index).attr('src','images/angle-down-solid.svg');
        }
    });
});