var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);


    var map;
    var mapContainer;
    var mapOption;
    var ps;

    $(document).ready(function () {
    // 마커 표시
    reloadMarker();

    //검색
    $(".search_wrapper.active").on("click", "#submitBtn", function() {
    searchPlaces();
});
})
; // document ready end

    // 상점 마커 표시 Ajax
    function reloadMarker(){
    var params = $("#actionForm").serialize();

    $.ajax({
    url : "MainAjax",
    type : "POST",
    datatype : "json",
    data : params,
    success : function(res){
    drawMarker(res.mMarker);
},
    error : function(request, status, error){
    console.log(request.responseText);
}
});
}

    // 지도 생성, 마커 표시
    function drawMarker(mMarker) {
    if(map == null) {
    mapContainer = document.getElementById('map'); //지도 표시할 div
    mapOption = {
    center: new kakao.maps.LatLng(37.5666805, 126.9784147), //지도의 중심 좌표 (서울 시청)
    level: 3 //지도의 확대 레벨
};

    //지도 생성
    map = new kakao.maps.Map(mapContainer, mapOption);

    // 장소 검색 객체
    ps = new kakao.maps.services.Places();
}

    // 마커 이미지 주소
    var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
    // 마커 이미지의 이미지 크기
    var imageSize = new kakao.maps.Size(24, 35);
    // 마커 이미지 생성
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    // 주소-좌표 변환 객체
    var geocoder = new kakao.maps.services.Geocoder();

    mMarker.forEach(function(data) {
    // 주소로 좌표 검색
    geocoder.addressSearch(data.MK_ADR, function(result, status) {
    if (status === kakao.maps.services.Status.OK) {
    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

    // 결과값으로 받은 위치를 마커로 표시합니다
    var marker = new kakao.maps.Marker({
    title: data.MK_NAME,
    map: map,
    position: coords,
    image : markerImage // 마커 이미지
});

    // 인포윈도우 닫기 버튼
    var iwRemoveable = true;

    // 인포윈도우로 장소에 대한 설명 표시
    var infowindow = new kakao.maps.InfoWindow({
    content : '<div id="goInfo">'+ data.MK_NAME +'</div>',
    removable : iwRemoveable,
    zIndex : 1
});

    // 마커에 클릭이벤트를 등록
    kakao.maps.event.addListener(marker, 'click', function() {
    // 다른 열린 인포윈도우는 닫게 함
    $("img[alt='close']").click();
    // 마커 위에 인포윈도우 표시
    infowindow.open(map, marker);
});
}
})
});
}


