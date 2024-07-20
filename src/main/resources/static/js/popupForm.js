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

    $.ajax({
        type: 'POST',
        url: '/popup/addPopup.do',
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
            // 글 등록 후 상세 글보기에 운영 시간 출력
            displayOperatingTimes(operatingTimes);
        },
        error: function(error) {
            console.error('Error:', error);
        }
    });
}

function displayOperatingTimes(times) {
    var detailsDiv = document.createElement('div');
}
           