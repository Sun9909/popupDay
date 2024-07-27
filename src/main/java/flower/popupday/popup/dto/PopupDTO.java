package flower.popupday.popup.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("popupDTO")
public class PopupDTO {
    private Long popup_id;
    private String title;
    private String info;
    private Date start_date;
    private Date end_date;
    private String content;
    private String address;
    private String brand_page;
    private Role role;
    private Long user_id;
    private Date created_date;
    private Long hits;
    private String monday_time;
    private String tuesday_time;
    private String wednesday_time;
    private String thursday_time;
    private String friday_time;
    private String saturday_time;
    private String sunday_time;
    private String parking;
    private String fee;
    private String user_nickname;
    private String thumbnail;
    private String image_file_name;

    // Enum for role
    public enum Role {
        승인, 미승인, 취소
    }

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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
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

    public String getMonday_time() {
        return monday_time;
    }

    public void setMonday_time(String monday_time) {
        this.monday_time = monday_time;
    }

    public String getTuesday_time() {
        return tuesday_time;
    }

    public void setTuesday_time(String tuesday_time) {
        this.tuesday_time = tuesday_time;
    }

    public String getWednesday_time() {
        return wednesday_time;
    }

    public void setWednesday_time(String wednesday_time) {
        this.wednesday_time = wednesday_time;
    }

    public String getThursday_time() {
        return thursday_time;
    }

    public void setThursday_time(String thursday_time) {
        this.thursday_time = thursday_time;
    }

    public String getFriday_time() {
        return friday_time;
    }

    public void setFriday_time(String friday_time) {
        this.friday_time = friday_time;
    }

    public String getSaturday_time() {
        return saturday_time;
    }

    public void setSaturday_time(String saturday_time) {
        this.saturday_time = saturday_time;
    }

    public String getSunday_time() {
        return sunday_time;
    }

    public void setSunday_time(String sunday_time) {
        this.sunday_time = sunday_time;
    }

    public String getParking() {return parking;}

    public void setParking(String parking) {this.parking = parking;}

    public String getFee() {return fee;}

    public void setFee(String fee) {this.fee = fee;}

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage_file_name() {
        return image_file_name;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
    }

}