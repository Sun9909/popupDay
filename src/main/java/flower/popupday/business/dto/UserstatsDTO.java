package flower.popupday.business.dto;

import org.springframework.stereotype.Component;

import java.util.Date;
@Component("userStatsDTO")
public class UserstatsDTO {
    private int popup_id;
    private Date viewed_at;
    private Date birth_date;
    private String gender;
    private int count;
    private String birth_month;


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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBirth_month() {
        return birth_month;
    }

    public void setBirth_month(String birth_month) {
        this.birth_month = birth_month;
    }
}
