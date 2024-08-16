document.addEventListener('DOMContentLoaded', function() {
    var customSelect = document.querySelector('.custom-select');
    var selected = customSelect.querySelector('.select-selected');
    var items = customSelect.querySelector('.select-items');
    var filterSelect = document.querySelector('#filterSelect');

    selected.addEventListener('click', function() {
        items.classList.toggle('select-show');
    });

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

            // 필터 변경 시 첫 페이지로 이동
            loadComment(value);
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
        loadComment(selectedValue);
    });

    // 초기 페이지 로드
    loadComment('popup-comment');
});

// 데이터를 가져오는 함수
function fetchComment(filter) {
    return fetch('/mypage/myComment.do', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ filter: filter })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 실패했습니다. 상태 코드: ' + response.status);
            }
            return response.json(); // 응답을 JSON으로 읽습니다.
        })
        .catch(error => {
            console.error('Error:', error);
            throw error; // 에러를 다시 던져서 호출 측에서 처리하게 함
        });
}

// 데이터를 화면에 로딩하는 함수
function renderComment(data) {
    const container = document.querySelector('.comlist-tb');
    if (container) {
        container.innerHTML = ''; // 기존 콘텐츠 지우기

        // 데이터 콘솔 출력
        console.log("렌더링할 데이터:", data);

        data.comment.forEach((comment, index) => {
            console.log(`댓글 데이터 ${index}:`, comment);
            const commentElement = document.createElement('tr');
            commentElement.classList.add('comment-lists');

            // HTML 문자열을 직접 구성
            commentElement.innerHTML = `
                <td>${comment.content}</td>
                <td>${comment.created_at}</td>
            `;

            container.appendChild(commentElement);
        });
    }
}

// 데이터를 가져와서 화면에 로딩하는 함수
function loadComment(filter) {
    fetchComment(filter)
        .then(data => {
            console.log('서버 응답:', data);
            renderComment(data);
        })
        .catch(error => {
            // 팝업 조회 실패 시, 오류 메시지를 표시
            Swal.fire({
                icon: 'error',
                text: '팝업 조회에 실패했습니다.',
                confirmButtonText: '확인'
            });
        });
}
