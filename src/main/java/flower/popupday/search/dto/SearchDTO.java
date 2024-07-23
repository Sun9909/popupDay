package flower.popupday.search.dto;

// 검색 데이터 전송 객체를 정의하는 클래스
public class SearchDTO {
    // 해시태그 ID를 저장하는 필드
    private Long hash_tag_id;
    // 해시태그를 저장하는 필드
    private String hash_tag;

    // 기본 생성자
    public SearchDTO() {}

    // 모든 필드를 포함하는 생성자
    public SearchDTO(Long hash_tag_id, String hash_tag) {
        this.hash_tag_id = hash_tag_id;
        this.hash_tag = hash_tag;
    }

    // 해시태그 ID에 대한 getter 메서드
    public Long getHash_tag_id() {
        return hash_tag_id;
    }

    // 해시태그 ID에 대한 setter 메서드
    public void setHash_tag_id(Long hash_tag_id) {
        this.hash_tag_id = hash_tag_id;
    }

    // 해시태그에 대한 getter 메서드
    public String getHash_tag() {
        return hash_tag;
    }

    // 해시태그에 대한 setter 메서드
    public void setHash_tag(String hash_tag) {
        this.hash_tag = hash_tag;
    }

    // 객체의 문자열 표현을 반환하는 메서드
    @Override
    public String toString() {
        return "SearchDTO{" +
                "hash_tag_id=" + hash_tag_id +
                ", hash_tag='" + hash_tag + '\'' +
                '}';
    }

    // popup_tbl 클래스 정의를 위해 준비한 주석 (아직 클래스 내용은 없음)
    //popup_tbl
}
