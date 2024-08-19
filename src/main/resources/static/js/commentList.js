document.addEventListener('DOMContentLoaded', function() {
    //모든 html이 로드 된 후 실행
    var customSelect = document.querySelector('.custom-select');
    var selected = customSelect.querySelector('.select-selected');
    var items = customSelect.querySelector('.select-items');
    var filterSelect = document.querySelector('#filterSelect');

    //드롭다운 클릭 이벤트(선택된 옵션 영역을 클릭하면 드롭다운 목록을 보여주거나 숨김)
    selected.addEventListener('click', function() {
        items.classList.toggle('select-show');
    });

    //드롭다운 항목 선택 처리
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
        .then(commentMap => {
            if (!commentMap.ok) {
                throw new Error('네트워크 응답이 실패했습니다. 상태 코드: ' + commentMap.status);
            }
            return commentMap.json(); // 응답을 JSON으로 읽습니다.
        })
        .catch(error => {
            console.error('Error:', error);
            throw error; // 에러를 다시 던져서 호출 측에서 처리하게 함
        });
}

// 데이터를 화면에 로딩하는 함수
function renderPopupComment(data) {
    const container = document.querySelector('.comlist-tb');
    if (container) {
        container.innerHTML = ''; // 기존 콘텐츠 지우기

        // 데이터 콘솔 출력
        console.log("렌더링할 데이터:", data);

        data.comment.forEach(comment => {
            const commentElement = document.createElement('div');
            commentElement.classList.add('review-item');

            commentElement.innerHTML = `
                <div class="review-header" style="display: flex; justify-content: space-between; align-items: flex-start;">
                    <span class="review-author" style="font-size: 20px;">${comment.user_id}</span>
                    <div class="review-rating" style="display: flex; gap: 4px; justify-content: flex-end;">
                        ${'★'.repeat(comment.rating)}
                        ${'☆'.repeat(5 - comment.rating)}
                    </div>
                </div>
                <div class="review-content">${comment.content}</div>
                <div class="review-date">
                    작성일: ${new Date(comment.updated_at).toLocaleDateString()}
                </div>
            `;

            container.appendChild(commentElement);
        });

        // data.comment.forEach((comment, index) => {
        //     console.log(`댓글 데이터 ${index}:`, comment);
        //     const commentElement = document.createElement('tr');
        //     commentElement.classList.add('comment-lists');
        //
        //     // HTML 문자열을 직접 구성
        //     commentElement.innerHTML = `
        //         <td>${comment.content}</td>
        //         <td>${comment.created_at}</td>
        //     `;
        //
        //     container.appendChild(commentElement);
        // });
    }
}

function renderReviewComment(data) {
    const container = document.querySelector('.comlist-tb');
    if (container) {
        container.innerHTML = ''; // 기존 콘텐츠 지우기

        data.comment.forEach(review => {
            const reviewElement = document.createElement('div');
            reviewElement.classList.add('review-container');

            reviewElement.innerHTML = `
                <div class="review-header" style="display: flex; justify-content: space-between; align-items: flex-start;">
                    <span class="review-author" style="font-size: 20px;">${review.user_id}</span>
                </div>
                <div class="review-content">${review.content}</div>
                <div class="review-date">
                    작성일: ${new Date(review.created_at).toLocaleDateString()}
                </div>
            `;

            container.appendChild(reviewElement);
        });
    }
}


// 데이터를 가져와서 화면에 로딩하는 함수
function loadComment(filter) {
    // fetchComment(filter)
    //     .then(data => {
    //         console.log('서버 응답:', data);
    //         renderComment(data);
    //     })
    fetchComment(filter)
        .then(data => {
            if (filter === 'popup-comment') {
                renderPopupComment(data);  // 팝업 댓글 렌더링
            } else if (filter === 'review-comment') {
                renderReviewComment(data); // 후기 댓글 렌더링
            }
        })
        .catch(error => {
            // 댓글 조회 실패 시, 오류 메시지를 표시
            Swal.fire({
                icon: 'error',
                text: '댓글 조회에 실패했습니다.',
                confirmButtonText: '확인'
            });
        });
}
