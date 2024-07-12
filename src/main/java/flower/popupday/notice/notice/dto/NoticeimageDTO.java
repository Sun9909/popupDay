package flower.popupday.notice.notice.dto;

import org.springframework.stereotype.Component;

//일반 클래스 주입
@Component("noticeimageDTO")

public class NoticeimageDTO {
    private Long notice_image_id; //imageFileNo 이미지번호
    private String imageFileName;
    private Long notice_id; //articleNo  이미지번호

    public Long getNotice_image_id() {return notice_image_id;}

    public void setNotice_image_id(Long notice_image_id) { this.notice_image_id = notice_image_id;}

    public String getImageFileName() {return imageFileName;}

    public void setImageFileName(String imageFileName) {this.imageFileName = imageFileName;}

    public Long getNotice_id() {return notice_id;}

    public void setNotice_id(Long notice_id) {this.notice_id = notice_id;}
}
