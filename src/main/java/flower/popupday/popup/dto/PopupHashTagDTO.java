package flower.popupday.popup.dto;

import org.springframework.stereotype.Component;

@Component("popupHashTagDTO")
public class PopupHashTagDTO {
    private int popup_id;
    private int hash_tag_id;

    public int getPopup_id() {
        return popup_id;
    }

    public void setPopup_id(int popup_id) {
        this.popup_id = popup_id;
    }

    public int getHashtag_id() {
        return hash_tag_id;
    }

    public void setHashtag_id(int hash_tag_id) {
        this.hash_tag_id = hash_tag_id;
    }
}