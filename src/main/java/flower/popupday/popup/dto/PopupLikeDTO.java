package flower.popupday.popup.dto;

public class PopupLikeDTO {

    public Long user_id;
    public Long popup_id;

    public PopupLikeDTO() {

    }

    public PopupLikeDTO(Long user_id, Long popup_id) {
        this.user_id = user_id;
        this.popup_id = popup_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getPopup_id() {
        return popup_id;
    }

    public void setPopup_id(Long popup_id) {
        this.popup_id = popup_id;
    }
}