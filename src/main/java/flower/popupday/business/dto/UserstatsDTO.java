package flower.popupday.business.dto;

import org.springframework.stereotype.Component;

import java.util.Date;
@Component("userStatsDTO")
public class UserstatsDTO {
    private long user_id;
    private int popup_id;
    private Date viewed_at;
    private long id;
    private Date birth_date;
    private String gender;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getPopup_id() {
        return popup_id;
    }

    public void setPopup_id(int popup_id) {
        this.popup_id = popup_id;
    }

    public Date getViewed_at() {
        return viewed_at;
    }

    public void setViewed_at(Date viewed_at) {
        this.viewed_at = viewed_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
