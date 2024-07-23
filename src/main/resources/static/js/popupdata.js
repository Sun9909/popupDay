function kakaopost() {
    new daum.Postcode({
        oncomplete: function (data) {
            document.querySelector("#zipcode").value=data.zonecode;
            document.querySelector("#address").value=data.address;
        }
    }).open();
}

function count_check(obj){
    var chkBox = document.getElementsByName("chk"); //name 값 chk를 불러오기
    var chkCnt = 0; //chkCnt 변수에 초기값을 0으로 설정
    for(var i=0; i<chkBox.length; i++) { //반복문으로 초기값, 조건식, 증감식 설정
        if(chkBox[i].checked) { //조건문으로 chkBox가 checked 됐을 경우
            chkCnt++; //1씩 증가
        }
    }
    if(chkCnt > 3) { //조건문으로 chkCnt가 3개보다 클 경우
        alert("3개까지 체크할 수 있습니다."); //alert를 띄우기
        obj.checked = false;  //false을 주어 alert를 띄운 뒤에 check 되지 않도록 설정
        return false;
    }
}

//이미지 미리보기 구현
function readImage(input) {
    if(input.files && input.files[0]) {
        let reader=new FileReader();
        reader.onload=function (event) {
            $('#preview').attr('src', event.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }else {
        $('#preview').attr('src', '#');
    }
}

//여러 개의 이미지 추가
let count=1; //초기값 지정
function fn_addFile() { //이미지 여러개 올리기 위한 함수
    $('#dock_file').append('<input class="file-loads" type="file" name="imgFile' + count + '">');
    count++;
}

// 해시태그 추가
let hash_count=1; //초기값 지정
function fn_addHash_tag() { //이미지 여러개 올리기 위한 함수
    if (hash_count <= 5) {
        $('#dock_hash_tag').append('<input type="text" name="hash_tag" class="hash_tag" oninput="adjustWidth(this)" maxlength="10" value="#">');
        hash_count++;
    }else {
        alert("해시태그는 5개까지만 추가할 수 있습니다.");
    }
}

function adjustWidth(input) {
    // 임시 스팬 요소를 만들어 입력된 텍스트의 너비를 계산
    const span = document.createElement('span');
    span.style.visibility = 'hidden';
    span.style.whiteSpace = 'nowrap';
    span.textContent = input.value;

    document.body.appendChild(span);
    const width = span.offsetWidth;
    document.body.removeChild(span);

    // 최소 너비를 50px로 설정하고 텍스트 길이에 따라 동적으로 너비 조절
    input.style.width = Math.max(50, width + 1) + 'px';
}

function removeHashSymbol(input) {
    // 입력된 값에서 #을 제거
    input.value = input.value.replace(/#/g, '');
}