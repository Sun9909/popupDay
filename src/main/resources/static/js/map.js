// 지도를 생성합니다
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 요소를 가져옵니다.
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심 좌표를 설정합니다.
        level: 3 // 지도의 확대 레벨을 설정합니다.
    };
var map = new kakao.maps.Map(mapContainer, mapOption); // 설정한 옵션으로 지도를 생성합니다.

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder(); // 주소를 좌표로 변환하는 Geocoder 객체를 생성합니다.

function searchAddress() {
    var address = document.getElementById('address').value; // 입력된 주소 값을 가져옵니다.
    if (address) { // 주소 값이 비어있지 않은지 확인합니다.
        // 주소로 좌표를 검색합니다
        geocoder.addressSearch(address, function(result, status) {
            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) { // 검색 상태가 정상일 경우
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x); // 검색 결과에서 좌표를 가져와 설정합니다.

                // 결과값으로 받은 위치를 마커로 표시합니다
                var marker = new kakao.maps.Marker({
                    map: map, // 마커를 표시할 지도 객체
                    position: coords // 마커의 위치를 설정합니다.
                });

                // 인포윈도우로 장소에 대한 설명을 표시합니다
                var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="width:150px;text-align:center;padding:6px 0;">' +
                        '지번 주소: ' + result[0].address.address_name + '<br>' +
                        '도로명 주소: ' + result[0].road_address.address_name + '</div>' // 인포윈도우의 내용을 설정합니다.
                });
                infowindow.open(map, marker); // 인포윈도우를 마커 위에 표시합니다.

                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords); // 지도의 중심을 검색된 좌표로 이동시킵니다.
            } else {
                alert('주소를 찾을 수 없습니다.'); // 검색이 실패한 경우 경고 메시지를 표시합니다.
            }
        });
    } else {
        alert('주소를 입력해주세요.'); // 주소 값이 비어있는 경우 경고 메시지를 표시합니다.
    }
}
