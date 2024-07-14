package flower.popupday.popup.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("imageDTO")
public class ImageDTO {
    private Long popup_image_id;
    private Long popup_id;
    private String image_file_name;



    public String getImage_file_name() {
        return image_file_name;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
    }


    public Long getPopup_id() {
        return popup_id;
    }

    public void setPopup_id(Long popup_id) {
        this.popup_id = popup_id;
    }


    public Long getPopup_image_id() {
        return popup_image_id;
    }

    public void setPopup_image_id(Long popup_image_id) {
        this.popup_image_id = popup_image_id;
    }
}
