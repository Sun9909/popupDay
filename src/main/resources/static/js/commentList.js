document.addEventListener('DOMContentLoaded', function() {
    // 모든 HTML이 로드된 후 실행
    var customSelect = document.querySelector('.custom-select');
    var selected = customSelect.querySelector('.select-selected');
    var items = customSelect.querySelector('.select-items');
    var filterSelect = document.querySelector('#filterSelect');
    const reviewItems = document.querySelectorAll('.review-item');
    const reviewContainers = document.querySelectorAll('.review-container');

    // 드롭다운 클릭 이벤트 (선택된 옵션 영역을 클릭하면 드롭다운 목록을 보여주거나 숨김)
    selected.addEventListener('click', function() {
        items.classList.toggle('select-show');
    });

    // 드롭다운 항목 선택 처리
    items.addEventListener('click', function(e) {
        if (e.target.tagName === 'DIV') {
            var value = e.target.getAttribute('data-value');
            var text = e.target.textContent;

            // 선택된 값을 기본 드롭다운에 설정
            if (filterSelect) {
                filterSelect.value = value;
            }
            selected.textContent = text;

            // 커스텀 드롭다운 업데이트
            var selectedItems = items.querySelectorAll('div');
            selectedItems.forEach(function(item) {
                item.classList.remove('selected');
            });
            e.target.classList.add('selected');

            // 드롭다운 닫기
            items.classList.remove('select-show');

            // 필터 변경 시 해당 댓글만 표시
            updateCommentDisplay(value);
        }
    });

    // 클릭 외부 시 드롭다운 닫기
    document.addEventListener('click', function(e) {
        if (!customSelect.contains(e.target)) {
            items.classList.remove('select-show');
        }
    });

    // 기본 드롭다운 변경 시 필터 업데이트
    $('#filterSelect').on('change', function() {
        var selectedValue = $(this).val();
        console.log('Selected value from select:', selectedValue); // Debugging
        updateCommentDisplay(selectedValue);
    });

    // 댓글 표시 업데이트 함수
    function updateCommentDisplay(selectedValue) {
        // 모든 리뷰 및 팝업 아이템 숨기기
        reviewItems.forEach(item => {
            item.style.display = 'none';
        });
        reviewContainers.forEach(container => {
            container.style.display = 'none';
        });

        // 선택한 값과 일치하는 리뷰 및 팝업 아이템만 표시
        if (selectedValue === 'popup-comment') {
            reviewItems.forEach(item => {
                item.style.display = 'block';
            });
        } else if (selectedValue === 'review-comment') {
            reviewContainers.forEach(container => {
                container.style.display = 'block';
            });
        }
    }

    // 초기 페이지 로드 - 처음 로드할 때 선택된 옵션에 따라 필터 적용
    const initialSelectedValue = filterSelect.value;
    updateCommentDisplay(initialSelectedValue);
});
