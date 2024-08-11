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