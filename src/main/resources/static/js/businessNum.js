function businessNumber(){ //사업자번호 인증 API 이용
    let num = document.getElementById('license').value; //사업자번호
    const data = {
        "b_no": [num] //폼 넘버 가져오기-기본 형식
    };
    console.log(data);
    $.ajax({
        url: "인증키주소",  // serviceKey 값을 xxxxxx에 입력
        type: "POST",
        data: JSON.stringify(data), // json 을 string으로 변환하여 전송
        dataType: "JSON",
        contentType: "application/json",
        accept: "application/json",
        success: function(result) {
            //console.log(data.b_no);
            //console.log(result.data[0]); 전체객체 뽑기
            console.log(result.data[0]['b_stt_cd']); //사업자 01 번 호출
            let valid = result.data[0]['b_stt_cd'];

            if (valid=='01'){
                msg1();
            }else {
                msg2();
            }

        },
        error: function(result) {
            console.log(result.responseText); //responseText의 에러메세지 확인
        }
    });

}

function msg1(){
    let msg = document.getElementById('regimessage');
    msg.innerHTML = "<br>사업자 회원가입이 가능합니다.";
}

function msg2(){
    let msg = document.getElementById('regimessage');
    msg.innerHTML = "<br>사업자 회원가입을 할 수 없습니다.";

}

function businessRegi(){ //다음으로 버튼 누르면 사업자일시 회원가입으로 이동.
    let num = document.getElementById('license').value; //사업자번호
    const data = {
        "b_no": [num]
    };
    $.ajax({
        url: "인증키주소",  // serviceKey 값을 xxxxxx에 입력
        type: "POST",
        data: JSON.stringify(data),
        dataType: "JSON",
        contentType: "application/json",
        accept: "application/json",
        success: function(result) {

            let valid = result.data[0]['b_stt_cd'];

            if (valid=='01'){
                alert("사업자 인증에 성공했습니다.");
                opener.parent.location='../register/businessRegister';
                window.close();
            }else {
                alert("사업자가 아닙니다. 사업자 회원가입을 진행할수 없습니다.");
                opener.parent.location='../register/agreement';
                window.close();
            }

        },
        error: function(result) {
            alert("에러가 발생했습니다.");
        }
    });
}
