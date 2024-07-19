package flower.popupday.popup.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("popupDTO")
public class PopupDTO {
    private Long popup_id;
    private String title;
    private String info;
    private String popup_hours;
    private String content;
    private String address;
    private String brand_page;
    private Role role;
    private Long user_id;
    private Date created_date;
    private Long hits;

    // Enum for role
    public enum Role {
        승인, 미승인, 승인취소
    }

    // Default constructor
    public PopupDTO() {

    }

    // Constructor with all fields
    public PopupDTO(Long popup_id, String title, String info, String popup_hours, String content, String address, String brand_page, Role role, Long user_id , Date created_date, Long hits) {
        this.popup_id = popup_id;
        this.title = title;
        this.info = info;
        this.popup_hours = popup_hours;
        this.content = content;
        this.address = address;
        this.brand_page = brand_page;
        this.role = role;
        this.user_id = user_id;
        this.created_date = created_date;
        this.hits=hits;
    }

    // Getters and Setters
    public Long getPopup_id() {
        return popup_id;
    }

    public void setPopup_id(Long popup_id) {
        this.popup_id = popup_id;
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

    public String getPopup_hours() {
        return popup_hours;
    }

    public void setPopup_hours(String time) {
        this.popup_hours = popup_hours;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrand_page() {
        return brand_page;
    }

    public void setBrand_page(String brand_page) {
        this.brand_page = brand_page;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

    // 값이 전달 안 될때 toString 호출해서 확인
    @Override
    public String toString() {
        return "PopupDTO{" +
                "popup_id=" + popup_id +
                ", title='" + title + '\'' +
                ", info='" + info + '\'' +
                ", popup_hours=" + popup_hours +
                ", content='" + content + '\'' +
                ", map='" + address + '\'' +
                ", brand_page='" + brand_page + '\'' +
                ", role=" + role +
                ", userId=" + user_id +
                ", created_date=" + created_date +
                ", hits=" + hits +
                '}';
    }
}