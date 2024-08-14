package flower.popupday.mypage.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;

@Component("mypopupDTO")
public class MyPopupDTO {
    private Long popup_id;
    private String title;
    private String info;
    private Time popup_hours;
    private String content;
    private String map; //map이 지도인가..? 위치인가?
    private String brandPage;
    private Role role;
    private Long user_id;
    private Date created_at;

    public MyPopupDTO() {

    }

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

    public Time getPopup_hours() {
        return popup_hours;
    }

    public void setPopup_hours(Time popup_hours) {
        this.popup_hours = popup_hours;
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getcreated_at() {
        return created_at;
    }

    public void setcreated_at(Date created_at) {
        this.created_at = created_at;
    }
}