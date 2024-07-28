$("#frames").on("load", function() {
    let head = $("#frames").contents().find("head");
    head.append("<link rel='stylesheet' href='/js/admin.css' type='text/css'>");
});

function adjustWidth2(input) {
    // 글자 수에 따른 너비를 ch 단위로 설정
    const length = input.value.length;
    input.style.width = (length * 15 + 15) + 'px';
}

// 페이지 로드 시, 모든 input 요소의 너비를 초기화
document.addEventListener('DOMContentLoaded', () => {
    const inputs = document.querySelectorAll('#tag');
    inputs.forEach(input => adjustWidth2(input));
});
