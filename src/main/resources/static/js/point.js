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

//이미지 가져오는 api
const onDrop = async(files) => { //서버에 파일 업로드
    let formData = new FormData();
    formData.append("api_key", "488814329663519");
    formData.append("upload_preset", "lek3r0ky");
    formData.append("timestamp", (Date.now() / 1000) | 0);
    formData.append(`file`, files[0]);

    const config = {
        header: { "Content-Type": "multipart/form-data" }
    }

    await axios.post('https://api.cloudinary.com/v1_1/dt1ftobia/image/upload', formData,config)
    .then(res=>{
        uploadPost(res.data.url)
    })
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

