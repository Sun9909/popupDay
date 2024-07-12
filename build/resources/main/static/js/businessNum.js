function corp_chk() {
    $("#corp_reg").val($("#corp_reg").val().replace(/[^0-9]/g, ""));
    reg_num = $("#corp_reg").val();

    if(!reg_num) {
        alert("사업자등록번호를 입력해주세요.");
        return false;
    }

    var data = {
        "b_no": [reg_num]
    };

    $.ajax({
        url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=gh2AUehoQ3GJd40ZAezpHi4%2Bvh%2Bn%2Fh0EISraeBwfkwbSf9j4ZZHDPyDVW5rNnYSUj3IkfjftqulQ%2FC45Kg8lOQ%3D%3D",  // serviceKey 값을 xxxxxx에 입력
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
                alert("확인되셨습니다.");
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