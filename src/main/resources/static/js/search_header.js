function search() {
    const query = document.getElementById('searchInput').value;
    window.location.href = `/search?query=${encodeURIComponent(query)}`;
}