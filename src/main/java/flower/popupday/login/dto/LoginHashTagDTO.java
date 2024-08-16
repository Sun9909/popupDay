package flower.popupday.login.dto;

public class LoginHashTagDTO {

    private Long user_hash_tag_id;
    private Long user_id;
    private Long hash_tag_id;
    private String hash_tag;



    // 기본 생성자
    public LoginHashTagDTO(Long user_id, Long hastagid) {
    }

    // 매개변수 있는 생성자
    public LoginHashTagDTO(Long user_hash_tag_id, Long user_id, Long hash_tag_id, String hash_tag) {
        this.user_hash_tag_id = user_hash_tag_id;
        this.user_id = user_id;
        this.hash_tag_id = hash_tag_id;
        this.hash_tag = hash_tag;
    }

    public Long getUser_hash_tag_id() {
        return user_hash_tag_id;
    }

    public void setUser_hash_tag_id(Long user_hash_tag_id) {
        this.user_hash_tag_id = user_hash_tag_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

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
        return "LoginHashTagDTO{" +
                "user_hash_tag_id=" + user_hash_tag_id +
                ", user_id=" + user_id +
                ", hash_tag_id=" + hash_tag_id +
                ", hash_ta=" + hash_tag +
                '}';
    }
}
