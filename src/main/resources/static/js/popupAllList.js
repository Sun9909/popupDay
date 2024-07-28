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
            loadPopups(value);
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
        loadPopups(selectedValue);
    });

    // 초기 페이지 로드
    loadPopups('all');
});

// 데이터를 가져오는 함수
function fetchPopups(filter) {
    return fetch('/popup/selectPopupList.do', {
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
function renderPopups(data) {
    const container = document.querySelector('.poplist-tb');
    if (container) {
        container.innerHTML = ''; // 기존 콘텐츠 지우기

        // 데이터 콘솔 출력
        console.log("렌더링할 데이터:", data);

        data.popupInfoList.forEach((popupInfo, index) => {
            console.log(`팝업 데이터 ${index}:`, popupInfo);
            const popup = popupInfo.popup;
            const thumbnailImage = popupInfo.thumbnailImage;

            const popupElement = document.createElement('tr');
            popupElement.classList.add('popup-lists');

            // HTML 문자열을 직접 구성
            popupElement.innerHTML = `
                <td>
                    ${thumbnailImage ? `
                    <div class="popimg-bg">
                        <img src="/popupDownload.do?popup_id=${popup.popup_id}&image_file_name=${thumbnailImage.image_file_name}"
                        width="100" alt="Thumbnail Image">
                    </div>
                    ` : `
                    <div class="popimg-bg">
                        <span>No Image</span>
                    </div>
                    `}
                </td>
                <td>
                    <a href="/popup/popupView.do?popup_id=${popup.popup_id}">${popup.title}</a>
                </td>
                <td>${popup.start_date} ~ ${popup.end_date}</td>
                
            `;

            container.appendChild(popupElement);
        });
    }
}

// 데이터를 가져와서 화면에 로딩하는 함수
function loadPopups(filter) {
    fetchPopups(filter)
        .then(data => {
            console.log('서버 응답:', data);
            renderPopups(data);
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
