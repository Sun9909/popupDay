
// mapContainer : HTML 문서에서 id가 'map'인 요소를 가져와서 지도를 표시할 컨테이너로 사용합니다.
var mapContainer = document.getElementById('map'),
    mapOption = {  // mapOption : 지도의 초기 설정을 정의하는 객체
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // center : 지도가 처음 표시될 때 중심이 되는 좌표를 설정합니다.
        level: 1 // level: 지도의 확대 레벨을 설정합니다. 숫자가 작을수록 더 확대된 상태
    };

// map : 위에서 설정한 mapContainer와 mapOption을 사용하여 카카오 지도를 생성
var map = new kakao.maps.Map(mapContainer, mapOption);

// geocoder: 주소를 좌표로 변환하거나 좌표를 주소로 변환하기 위한 객체를 생성
var geocoder = new kakao.maps.services.Geocoder();

//marker: 사용자가 지도를 클릭한 위치를 표시할 마커 객체를 생성
var marker = new kakao.maps.Marker(),
    //infowindow: 사용자가 클릭한 위치의 상세 주소 정보를 표시할 인포윈도우 객체를 생성, zindex는 다른 요소 위에 표시되도록 설정합니다.
    infowindow = new kakao.maps.InfoWindow({zindex: 1});

// 현재 지도 중심 좌표를 가져와서 searchAddrFromCoords 함수에 전달하여 주소를 검색
// displayCenterInfo: 검색된 주소 정보를 지도 좌측 상단에 표시하는 함수
searchAddrFromCoords(map.getCenter(), displayCenterInfo);

// 지도를 클릭할 때 발생하는 이벤트를 처리하기 위해 이벤트 리스너를 등록
// mouseEvent: 클릭 이벤트에 대한 정보를 담고 있습니다.
kakao.maps.event.addListener(map, 'click', function (mouseEvent) {
    //searchDetailAddrFromCoords: 클릭한 위치의 좌표(mouseEvent.latLng)를 사용하여 상세 주소 정보를 검색
    //result: 검색 결과를 담고 있는 배열
    //status: 검색 결과의 상태를 나타내는 값
    searchDetailAddrFromCoords(mouseEvent.latLng, function (result, status) {
        //검색 상태가 정상(OK)인 경우에만 실행
        //result[0].road_address가 존재하면 도로명 주소를 detailAddr 변수에 저장하고, 그렇지 않으면 빈 문자열을 저장
        if (status === kakao.maps.services.Status.OK) {
            var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
            //지번 주소를 detailAddr 변수에 추가
            detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';

            //인포윈도우에 표시할 HTML 형식의 내용을 작성
            var content = '<div class="bAddr">' +
                '<span class="title">법정동 주소정보</span>' +
                detailAddr +
                '</div>';

            //마커의 위치를 클릭한 위치(mouseEvent.latLng)로 설정하고, 지도의 해당 위치에 마커를 표시
            marker.setPosition(mouseEvent.latLng);
            marker.setMap(map);

            // 인포윈도우의 내용을 content로 설정하고, 마커 위에 인포윈도우를 표시
            infowindow.setContent(content);
            infowindow.open(map, marker);
        }
    });
});

// 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'idle', function () {
    searchAddrFromCoords(map.getCenter(), displayCenterInfo);
});

//searchAddrFromCoords: 주어진 좌표(coords)를 사용하여 행정동 주소 정보를 요청
function searchAddrFromCoords(coords, callback) {
    // coords.getLng(): 좌표의 경도(Lng)를 가져옴
    // coords.getLat(): 좌표의 위도(Lat)를 가져옴
    // callback: 검색 결과를 처리할 콜백 함수를 지정
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
}

//searchDetailAddrFromCoords: 주어진 좌표(coords)를 사용하여 법정동 상세 주소 정보를 요청
function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

// displayCenterInfo: 지도 중심 좌표의 주소 정보를 지도 좌측 상단에 표시하는 함수
// result: 주소 검색 결과 배열
// status: 검색 결과의 상태를 나타내는 값
function displayCenterInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        //infoDiv: 지도 좌측 상단에 주소 정보를 표시할 HTML 요소
        var infoDiv = document.getElementById('centerAddr');

        for (var i = 0; i < result.length; i++) {
            // result 배열을 순회하며, region_type이 'H'인 행정동 정보를 찾습니다.
            if (result[i].region_type === 'H') {
                infoDiv.innerHTML = result[i].address_name;
                break; // break : 해당 정보를 찾으면, infoDiv에 주소를 표시하고 반복문을 종료
            }
        }
    }
}