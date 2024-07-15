package flower.popupday.popup.dto;

import org.springframework.stereotype.Component;

@Component("hashTagDTO")
public class HashTagDTO {
    private int popup_id;
    private String hash_tag;

    public int getPopup_id() {
        return popup_id;
    }

    public void setPopup_id(int popup_id) {
        this.popup_id = popup_id;
    }

    public String getHash_tag() {
        return hash_tag;
    }

    public void setHash_tag(String hash_tag) {
        this.hash_tag = hash_tag;
    }
}