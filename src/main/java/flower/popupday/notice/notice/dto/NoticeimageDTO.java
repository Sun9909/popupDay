package flower.popupday.notice.notice.dto;

import org.springframework.stereotype.Component;

//일반 클래스 주입
@Component("noticeimageDTO")

public class NoticeimageDTO {
    private Long notice_image_id;
    private Long notice_id;
    private String image_file_name;


    public Long getNotice_image_id() {return notice_image_id;}

    public void setNotice_image_id(Long notice_image_id) { this.notice_image_id = notice_image_id;}

    public String getImage_file_name() {return image_file_name;}

    public void setImage_file_name(String image_file_name) {this.image_file_name = image_file_name;}

    public Long getNotice_id() {return notice_id;}

    public void setNotice_id(Long notice_id) {this.notice_id = notice_id;}
}
