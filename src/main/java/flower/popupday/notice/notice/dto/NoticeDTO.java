package flower.popupday.notice.notice.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("noticeDTO")
public class NoticeDTO {
    private long notice_id;
    private long user_id;
    private String title;
    private String content;
    private Date created_date;

    // NoticeDTO 선언
    public NoticeDTO() {}

    public NoticeDTO(long notice_id, long user_id, String title, String content, Date created_date) {
        this.notice_id = notice_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.created_date = created_date;
    }

    public long getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(long notice_id) {
        this.notice_id = notice_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
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
}
