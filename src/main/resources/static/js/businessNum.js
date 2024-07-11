function code_check(){
    if(!checkInput_null('frm1','code1,code2,code3')){ frm1.overlap_code_ok.value = "";}
    else{
        document.frm1.code.value = frm1.code1.value + frm1.code2.value + frm1.code3.value;
        var data = {
            "b_no":[document.frm1.code.value]
        };

        $.ajax({
            url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=[gh2AUehoQ3GJd40ZAezpHi4%2Bvh%2Bn%2Fh0EISraeBwfkwbSf9j4ZZHDPyDVW5rNnYSUj3IkfjftqulQ%2FC45Kg8lOQ%3D%3D]", //활용 신청 시 발급 되는 serviceKey값을 [서비스키]에 입력해준다.
            type: "POST",
            data: JSON.stringify(data), // json 을 string으로 변환하여 전송
            dataType: "JSON",
            traditional: true,
            contentType: "application/json; charset:UTF-8",
            accept: "application/json",
            success: function(result) {
                console.log(result);
                if(result.match_cnt == "1") {
                    //성공
                    console.log("success");
                    document.frm1.submit();
                } else {
                    //실패
                    console.log("fail");
                    alert(result.data[0]["tax_type"]);
                }
            },
            error: function(result) {
                console.log("error");
                console.log(result.responseText); //responseText의 에러메세지 확인
            }
        });
    }
}