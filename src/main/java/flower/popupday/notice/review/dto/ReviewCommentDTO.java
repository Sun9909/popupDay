package flower.popupday.notice.review.dto;

import java.time.LocalDateTime;

public class ReviewCommentDTO {
    private Long review_comment_id;
    private Long user_id;
    private Long review_id;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String user_nickname;

    public Long getReview_comment_id() {
        return review_comment_id;
    }

    public void setReview_comment_id(Long review_comment_id) {
        this.review_comment_id = review_comment_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getReview_id() {
        return review_id;
    }

    public void setReview_id(Long review_id) {
        this.review_id = review_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }
}
