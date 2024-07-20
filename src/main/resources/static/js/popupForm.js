// 폼 제출 시 운영 시간 정보를 로컬 스토리지에 저장
function submitForm(event) {
    event.preventDefault();
    var form = document.getElementById('popupStoreForm');
    var formData = new FormData(form);

    var operatingTimes = {
        mondayTime: formData.get('mondayTime'),
        tuesdayTime: formData.get('tuesdayTime'),
        wednesdayTime: formData.get('wednesdayTime'),
        thursdayTime: formData.get('thursdayTime'),
        fridayTime: formData.get('fridayTime'),
        saturdayTime: formData.get('saturdayTime'),
        sundayTime: formData.get('sundayTime')
    };

    // 데이터 로컬 스토리지에 저장
    localStorage.setItem('operatingTimes', JSON.stringify(operatingTimes));

    // 서버로 폼 데이터 전송
    $.ajax({
        type: 'POST',
        url: '/popup/addPopup.do',
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
            displayOperatingTimes();
        },
        error: function(error) {
            console.error('Error:', error);
        }
    });
}

// 로컬 스토리지에서 운영 시간 정보를 불러와서 표시
function displayOperatingTimes() {
    var operatingTimes = JSON.parse(localStorage.getItem('operatingTimes'));

    if (operatingTimes) {
        var detailsDiv = document.createElement('div');
        detailsDiv.innerHTML = `
            <h3>운영 시간</h3>
            <p>월: ${operatingTimes.mondayTime}</p>
            <p>화: ${operatingTimes.tuesdayTime}</p>
            <p>수: ${operatingTimes.wednesdayTime}</p>
            <p>목: ${operatingTimes.thursdayTime}</p>
            <p>금: ${operatingTimes.fridayTime}</p>
            <p>토: ${operatingTimes.saturdayTime}</p>
            <p>일: ${operatingTimes.sundayTime}</p>
        `;
        document.body.appendChild(detailsDiv);
    }
}

// 페이지 로드 시 운영 시간 표시
window.onload = function() {
    displayOperatingTimes();
};
