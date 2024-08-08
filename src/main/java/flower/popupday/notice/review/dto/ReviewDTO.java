package flower.popupday.notice.review.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("reviewDTO")
public class ReviewDTO {
    private long review_id;
    private long user_id;
    private String review_title;
    private String review_content;
    private Date created_at;
    private Date updated_at;


    public ReviewDTO() {}

    public ReviewDTO(long review_id, long user_id, String review_title, String review_content,Date created_at, Date updated_at) {
        this.review_id = review_id;
        this.user_id = user_id;
        this.review_title = review_title;
        this.review_content = review_content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }


    public long getReview_id() {
        return review_id;
    }

    public void setReview_id(long review_id) {
        this.review_id = review_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getReview_title() {
        return review_title;
    }

    public void setReview_title(String review_title) {
        this.review_title = review_title;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
