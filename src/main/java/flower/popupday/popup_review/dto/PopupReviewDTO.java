package flower.popupday.popup_review.dto;

import java.time.LocalDateTime;

public class PopupReviewDTO {
    private long popup_comment_id;
    private long popup_id;
    private long user_id;
    private String content;
    private int rating;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String user_nickname;

    // Getters and Setters
    public long getPopup_comment_id() {
        return popup_comment_id;
    }

    public void setPopup_comment_id(long popup_comment_id) {
        this.popup_comment_id = popup_comment_id;
    }

    public long getPopup_id() {
        return popup_id;
    }

    public void setPopup_id(long popup_id) {
        this.popup_id = popup_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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