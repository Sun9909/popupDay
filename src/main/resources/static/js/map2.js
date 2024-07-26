//popupView에서 address로 맵 받아오는 js

document.addEventListener("DOMContentLoaded", function() {
    var address = document.getElementById('address').value;

    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(address, function(result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {

            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 지도를 표시할 div와 옵션을 설정합니다
            var staticMapContainer  = document.getElementById('staticMap'),
                staticMapOption = {
                    center: coords, // 지도의 중심좌표
                    level: 3, // 지도의 확대 레벨
                    marker: {
                        position: coords
                    }
                };

            // 이미지 지도를 생성합니다
            var staticMap = new kakao.maps.StaticMap(staticMapContainer, staticMapOption);
        } else {
            console.error('Geocode was not successful for the following reason: ' + status);
        }
    });
});