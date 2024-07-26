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

document.addEventListener('DOMContentLoaded', function() {
    var fileInput = document.getElementById('imageFiles');
    var dockFileDiv = document.getElementById('dock_file');
    var fileCountElem = document.getElementById('fileCount');
    var currentFiles = []; // 현재 파일 목록

    // 사용자 정의 버튼 클릭 시 파일 입력 요소 클릭
    document.getElementById('customFileButton').addEventListener('click', function() {
        fileInput.click();
    });

    // 파일 선택 시 파일 리스트 업데이트
    fileInput.addEventListener('change', function() {
        var selectedFiles = Array.from(fileInput.files);

        if (selectedFiles.length === 0) {
            // 파일이 선택되지 않은 경우, 기존 리스트를 유지
            return;
        }

        // 기존 파일 목록과 선택된 파일을 병합
        currentFiles = Array.from(new Set([...currentFiles, ...selectedFiles]));

        updateFileList(currentFiles);
        updateFileCount();
    });

    // 파일 리스트 업데이트 함수
    function updateFileList(fileList) {
        dockFileDiv.innerHTML = ''; // 기존 파일 리스트 초기화

        fileList.forEach(function(file) {
            var fileItem = document.createElement('div');
            fileItem.className = 'file-item';

            // 이미지 미리보기
            var img = document.createElement('img');
            img.className = 'file-preview';
            img.file = file;
            fileItem.appendChild(img);

            var removeButton = document.createElement('button');
            const tagp = document.createElement('p');
            tagp.textContent = 'x';
            removeButton.appendChild(tagp);
            removeButton.className = 'remove-file';
            removeButton.onclick = function() {
                // 파일 삭제 시 업데이트된 파일 목록 생성
                currentFiles = currentFiles.filter(f => f !== file);
                var dataTransfer = new DataTransfer();
                currentFiles.forEach(f => dataTransfer.items.add(f));
                fileInput.files = dataTransfer.files; // 업데이트된 파일 목록 적용

                // 파일 리스트와 개수 업데이트
                updateFileList(currentFiles);
                updateFileCount();
            };

            fileItem.appendChild(removeButton);
            dockFileDiv.appendChild(fileItem);

            // FileReader를 사용하여 파일을 읽고 미리보기 표시
            var reader = new FileReader();
            reader.onload = (function(aImg) {
                return function(e) {
                    aImg.src = e.target.result;
                };
            })(img);
            reader.readAsDataURL(file);
        });
    }

    // 파일 개수 업데이트
    function updateFileCount() {
        var fileCount = currentFiles.length;
        fileCountElem.textContent = fileCount === 0 ? '0개' : fileCount + '개';
    }

    // 파일 리스트 초기화 버튼 클릭 이벤트
    document.getElementById('resetButton').addEventListener('click', function() {
        fileInput.value = ''; // 파일 인풋 초기화
        dockFileDiv.innerHTML = ''; // 파일 리스트 초기화
        currentFiles = []; // 현재 파일 목록 초기화
        updateFileCount(); // 파일 개수 초기화
    });
});

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