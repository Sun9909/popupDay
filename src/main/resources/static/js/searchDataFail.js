document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('searchForm').addEventListener('submit', function (e) {
        e.preventDefault(); // 기본 폼 제출을 막음
        submitForm();
    });
});

function submitForm() {
    var query = document.getElementById('searchInput1').value;
    fetch(`/search/api?query=${query}`)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                displayResults(data.data);
            } else {
                Swal.fire({
                    icon: 'error',
                    title: '검색 결과가 없습니다.',
                    text: '팝업 목록 페이지로 이동합니다.',
                    showConfirmButton: true
                }).then(() => {
                    window.location.href = '/popup/popupAllList.html';
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: '검색 중 오류가 발생했습니다.',
                text: error.message,
                showConfirmButton: true
            });
        });
}

function displayResults(results) {
    var resultsDiv = document.getElementById("searchResults");
    resultsDiv.innerHTML = "";
    results.forEach(result => {
        var resultElement = document.createElement("div");
        resultElement.textContent = result.hashTag;
        resultsDiv.appendChild(resultElement);
    });
}