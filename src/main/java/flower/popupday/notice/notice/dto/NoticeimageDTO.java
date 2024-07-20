package flower.popupday.notice.notice.dto;

import org.springframework.stereotype.Component;

@Component("noticeimageDTO")
public class NoticeimageDTO {
    private long notice_image_id;
    private long notice_id;
    private String image_file_name;


    public long getNotice_image_id() {
        return notice_image_id;
    }

    public void setNotice_image_id(long notice_image_id) {
        this.notice_image_id = notice_image_id;
    }

    public long getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(long notice_id) {
        this.notice_id = notice_id;
    }

    public String getImage_file_name() {
        return image_file_name;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
    }
}
