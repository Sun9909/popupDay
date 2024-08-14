function updateCharacterCount() {
    // 텍스트 영역과 남은 글자 수를 표시하는 요소를 가져옴
    const textarea = document.getElementById('content');
    const charCount = document.getElementById('charCount');

    // 남은 글자 수 계산
    const remaining = 50 - textarea.value.length;

    // 남은 글자 수를 (남은 글자 / 최대 글자) 형식으로 화면에 업데이트
    charCount.textContent = `(${remaining} / 50)`;
}