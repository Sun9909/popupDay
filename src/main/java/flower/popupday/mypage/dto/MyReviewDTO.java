package flower.popupday.mypage.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("myreviewDTO")
public class MyReviewDTO {
    private Long review_id; //리뷰 번호
    private Long user_id;   //회원번호
    private String review_title;    //리뷰 제목
    private String review_content;  //리뷰 내용
    private Date date;  //최종 날짜?
    private String write;   //작성자
    private Date created_at;  //기재 날짜

    //생성자
    public MyReviewDTO() {

    }

    public Long getReview_id() {
        return review_id;
    }

    public void setReview_id(Long review_id) {
        this.review_id = review_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
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

    public Date getcreated_at() {
        return created_at;
    }

    public void setcreated_at(Date created_at) {
        this.created_at = created_at;
    }
}