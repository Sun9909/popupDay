package flower.popupday.notice.notice.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("noticeDTO")
public class NoticeDTO {
    private Long notice_id;
    private Long user_id;
    private String title;
    private String content;
    private Date created_date;

    // NoticeDTO 선언
    public NoticeDTO() {}

    // 모든 필드를 포함하는 생성자
    public NoticeDTO(Long notice_id, Long user_id, String title, String content, Date created_date, String write) {
        this.notice_id = notice_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.created_date = created_date;
    }

    // Getter,Setter 생성
    public Long getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(Long notice_id) {
        this.notice_id = notice_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    //값이 전달 안 될때 toString 호풀해서 선언
    @Override
    public String toString() {
        return "NoticeDTO{" +
                "notice_id=" + notice_id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", created_date=" + created_date +
                '}';
    }

}
