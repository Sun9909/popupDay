package flower.popupday.review.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("reviewDTO")
public class ReviewDTO {
    private long review_id;
    private long user_id;
    private String review_title;
    private String review_content;
    private Date date;
    private String write;
    private Date created_date;


    public ReviewDTO() {}

    public ReviewDTO(long review_id, long user_id, String review_title, String review_content,Date date, String write, Date created_date) {
        this.review_id = review_id;
        this.user_id = user_id;
        this.review_title = review_title;
        this.review_content = review_content;
        this.date = date;
        this.write = write;
        this.created_date = created_date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

}
