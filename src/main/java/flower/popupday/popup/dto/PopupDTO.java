package flower.popupday.popup.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;

@Component("popupDTO")
public class PopupDTO {
    private Long popupId;
    private String title;
    private String info;
    private String time;
    private String content;
    private String map;
    private String brandPage;
    private Role role;
    private Long userId;
    private Date created_date;

    // Enum for role
    public enum Role {
        승인, 미승인, 승인취소
    }

    // Default constructor
    public PopupDTO() {

    }

    // Constructor with all fields
    public PopupDTO(Long popupId, String title, String info, String time, String content, String map, String brandPage, Role role, Long userId , Date created_date) {
        this.popupId = popupId;
        this.title = title;
        this.info = info;
        this.time = time;
        this.content = content;
        this.map = map;
        this.brandPage = brandPage;
        this.role = role;
        this.userId = userId;
        this.created_date = created_date;
    }

    // Getters and Setters
    public Long getPopupId() {
        return popupId;
    }

    public void setPopupId(Long popupId) {
        this.popupId = popupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getBrandPage() {
        return brandPage;
    }

    public void setBrandPage(String brandPage) {
        this.brandPage = brandPage;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    // 값이 전달 안 될때 toString 호출해서 확인
    @Override
    public String toString() {
        return "PopupDTO{" +
                "popupId=" + popupId +
                ", title='" + title + '\'' +
                ", info='" + info + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                ", map='" + map + '\'' +
                ", brandPage='" + brandPage + '\'' +
                ", role=" + role +
                ", userId=" + userId +
                ", created_date=" + created_date +
                '}';
    }
}
