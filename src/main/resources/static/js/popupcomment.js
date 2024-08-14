function updateCharacterCount() {
    // 텍스트 영역과 남은 글자 수를 표시하는 요소를 가져옴
    const textarea = document.getElementById('content');
    const charCount = document.getElementById('charCount');

    // 남은 글자 수 계산
    const remaining = 50 - textarea.value.length;

    // 남은 글자 수를 (남은 글자 / 최대 글자) 형식으로 화면에 업데이트
    charCount.textContent = `(${remaining} / 50)`;
}
function toggleOptionsMenu(button) {
    // 다른 열린 메뉴들을 모두 닫음
    document.querySelectorAll('.review-options-menu').forEach(menu => {
        if (menu !== button.nextElementSibling) {
            menu.style.display = 'none';
        }
    });

    // 현재 클릭한 메뉴의 표시 여부를 토글함
    const menu = button.nextElementSibling;
    menu.style.display = (menu.style.display === 'none' || menu.style.display === '') ? 'block' : 'none';
}

// 클릭을 메뉴 외부에서 발생한 경우 메뉴 닫기
document.addEventListener('click', function (event) {
    const isClickInside = event.target.closest('.review-options');
    if (!isClickInside) {
        document.querySelectorAll('.review-options-menu').forEach(menu => {
            menu.style.display = 'none';
        });
    }
});