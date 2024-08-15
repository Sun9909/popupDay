package flower.popupday.login.dto;

import org.springframework.stereotype.Component;

@Component("loginhashtagDTO")
public class LoginHashTagDTO {

    private Long user_id;
    private Long has_tag_id;

    // 기본 생성자
    public LoginHashTagDTO() {
    }

    public LoginHashTagDTO(Long user_id, Long has_tag_id) {
        this.user_id = user_id;
        this.has_tag_id = has_tag_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getHas_tag_id() {
        return has_tag_id;
    }

    public void setHas_tag_id(Long has_tag_id) {
        this.has_tag_id = has_tag_id;
    }
}
