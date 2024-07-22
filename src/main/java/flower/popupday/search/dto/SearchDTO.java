package flower.popupday.search.dto;

public class SearchDTO {
    private Long hash_tag_id;
    private String hash_tag;

    // 기본 생성자
    public SearchDTO() {}

    // 모든 필드를 포함하는 생성자
    public SearchDTO(Long hash_tag_id, String hash_tag) {
        this.hash_tag_id = hash_tag_id;
        this.hash_tag = hash_tag;
    }

    // Getter 및 Setter 메서드
    public Long getHash_tag_id() {
        return hash_tag_id;
    }

    public void setHash_tag_id(Long hash_tag_id) {
        this.hash_tag_id = hash_tag_id;
    }

    public String getHash_tag() {
        return hash_tag;
    }

    public void setHash_tag(String hash_tag) {
        this.hash_tag = hash_tag;
    }

    @Override
    public String toString() {
        return "SearchDTO{" +
                "hash_tag_id=" + hash_tag_id +
                ", hash_tag='" + hash_tag + '\'' +
                '}';
    }

    //popup_tbl

}
