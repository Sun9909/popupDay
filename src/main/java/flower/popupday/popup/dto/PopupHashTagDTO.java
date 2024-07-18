package flower.popupday.popup.dto;

import org.springframework.stereotype.Component;

@Component("popupHashTagDTO")
public class PopupHashTagDTO {
    private Long popup_id;
    private Long hash_tag_id;

    public Long getPopup_id() {
        return popup_id;
    }

    public void setPopup_id(Long popup_id) {
        this.popup_id = popup_id;
    }

    public Long getHashtag_id() {
        return hash_tag_id;
    }

    public void setHashtag_id(Long hash_tag_id) {
        this.hash_tag_id = hash_tag_id;
    }
}