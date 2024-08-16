function goodsImage(input) {
    if(input.files && input.files[0]) {
        let reader=new FileReader();
        reader.onload=function (event) {
            console.log(event);
            $("#preview").attr("src",event.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }else {
        $("#preview").attr("src","#");
    } // if end
}

function showImage() {
    // 선택된 라디오 버튼의 값을 가져옵니다.
    const selectedValue = document.querySelector('input[name="image_name"]:checked').value;

    // Cloudinary 이미지 URL 생성 (여기서는 간단히 예제 URL을 사용합니다)
    const cloudinaryBaseURL = "https://res.cloudinary.com/dt1ftobia/image/upload/";
    let imageURL;

    switch (selectedValue) {
        case "바나나맛 우유":
            imageURL = cloudinaryBaseURL + "v1723428042/quzroryrpuzkfc5gtno0.png";
            break;
        case "베스킨라빈스 싱글콘":
            imageURL = cloudinaryBaseURL + "v1723428042/btakyskddcqmzonbhrv1.png";
            break;
        case "올리브영 1만원 교환권":
            imageURL = cloudinaryBaseURL + "v1723428043/w96tbtbigsoetebac9be.png";
            break;
        case "투썸플레이스 아메리카노 + 아이스박스 세트":
            imageURL = cloudinaryBaseURL + "v1723428040/fwu79bfv54k4lz5biazl.png";
            break;
        case "스타벅스 아메리카노2 + 부드러운 생크림 카스테라":
            imageURL = cloudinaryBaseURL + "v1723428041/ql92hzvixdw1lcy6yinc.png";
            break;
        case "투썸플레이스 스트로베리 초코 생크림 케이크":
            imageURL = cloudinaryBaseURL + "v1723428041/qwzgax42rh66y7dbmpa0.png";
            break;
        default:
            imageURL = ""; // 기본 이미지 URL
    }

    // hidden input 필드에 URL을 설정합니다
    document.querySelector('#imageURL').value = imageURL;
}

//상품삭제
function fn_remove_goods(url, shop_id){
    let del_goods_form = document.createElement("form");
    del_goods_form.setAttribute("action", url);
    del_goods_form.setAttribute("method","post");
    let goodsNoInput = document.createElement("input");
    goodsNoInput.setAttribute("type","hidden");
    goodsNoInput.setAttribute("name","shop_id");
    goodsNoInput.setAttribute("value", shop_id);
    del_goods_form.appendChild(goodsNoInput);
    document.body.appendChild(del_goods_form);
    del_goods_form.submit();
}

//교환개수 누를때마다 필요한 포인트 알려주는 함수
document.getElementById("product-count").addEventListener("input", point_mover)

function point_mover(event){
    change_count = parseInt(event.target.value, 10) //10진수로 나오게
    const privious_siblings = event.target.previousElementSibling
    const some = parseInt(privious_siblings.previousElementSibling.value, 10)

    if (event.type === "input") {
        if (change_count == 1){
            event.target.previousElementSibling.value = some
        }
        event.target.previousElementSibling.value = some * change_count
    }
}


//교환 submit 버튼 누르면 시리얼번호 이미지 하단에 붙여서 보내주는 함수

function generateSerialNumber() {
    const timestamp = new Date().getTime();
    const randomNum = Math.floor(Math.random() * 10000);
    return `SN-${timestamp}-${randomNum}`;
}

async function drawImageWithSerialNumber(imageSrc) {
    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');

    const img = new Image();
    img.src = imageSrc;
    img.crossOrigin = 'Anonymous'; // Cross-Origin 문제를 피하기 위한 설정

    img.onload = async function() {

        const width = canvas.width;  // CSS에서 설정한 너비
        const height = 170; // CSS에서 설정한 높이
        const serialNumber = generateSerialNumber();

        // Canvas 크기 설정
        const paddingTop = 0; // 상단 패딩
        const paddingRight = 5; // 우측 패딩
        const paddingBottom = 5; // 하단 패딩
        const paddingLeft = 5; // 좌측 패딩

        const fontSize = 15; // 시리얼 번호 폰트 크기
        const backgroundHeight = fontSize + paddingTop + paddingBottom; // 배경 높이
        canvas.height = height + backgroundHeight; // 시리얼 번호를 위한 추가 공간

        ctx.clearRect(0, 0, canvas.width, canvas.height);
        // 이미지 그리기
        ctx.drawImage(img, 0, 0, width, height);

        // 시리얼 번호 배경 설정
        ctx.fillStyle = 'white'; // 배경색을 흰색으로 설정
        ctx.fillRect(0, height, width, backgroundHeight); // 배경 영역을 그립니다.

        // 시리얼 번호 스타일 설정
        ctx.font = '13px Arial';
        ctx.fillStyle = '#333';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'top';
        ctx.fillText(serialNumber, width / 2, height + backgroundHeight / 2 - 6);

        // 수정된 이미지를 Cloudinary에 업로드
        try {
            const imageURL = await uploadToCloudinary(canvas);
            return imageURL
        } catch (error) {
            console.error('Error uploading image:', error);
        }
    };
}

document.getElementById("gifticon").addEventListener('click', function() {
    const user_point = parseInt(document.querySelector('#user_point').value,10);
    const goods_point = parseInt(this.parentElement.querySelector('#product_price').value,10)
    if (user_point >= goods_point){
        event.preventDefault();
            const imageSrc = this.parentElement.querySelector('#file_name').value;
             drawImageWithSerialNumber(imageSrc)
                .then(changedsrc => {
                    console.log(changedsrc);
                    this.parentElement.querySelector('#file_name').value = changedsrc; // 변경된 이미지 소스 값을 업데이트합니다

                    // 지연 후 폼을 제출합니다
                    setTimeout(() => {
                        alert('교환에 성공했습니다!');
                        this.parentElement.submit();
                    }, 1000);
                })
                .catch(error => {
                    console.error('이미지 처리 중 오류 발생:', error);
                });
    }else {
        alert('포인트가 부족합니다.');
    }

});


async function uploadToCloudinary(canvas) {
    const CLOUDINARY_URL = 'https://api.cloudinary.com/v1_1/dt1ftobia/image/upload';
    const UPLOAD_PRESET = 'lek3r0ky';

    const dataURL = canvas.toDataURL('image/png'); // 캔버스의 데이터를 URL로 변환
    const formData = new FormData();
    formData.append('file', dataURL);
    formData.append('upload_preset', UPLOAD_PRESET);

    try {
        const response = await fetch(CLOUDINARY_URL, {
            method: 'POST',
            body: formData,
        });

        if (!response.ok) {
            throw new Error('Failed to upload image');
        }

        const result = await response.json();
        return result.secure_url; // 업로드된 이미지 URL 반환
    } catch (error) {
        console.error('Error uploading image:', error);
    }
}