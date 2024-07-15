package flower.popupday.popup.dto;

import org.springframework.stereotype.Component;

@Component("hashTagDTO")
public class HashTagDTO {
    private Long hash_tag_id;
    private String hash_tag;

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
}