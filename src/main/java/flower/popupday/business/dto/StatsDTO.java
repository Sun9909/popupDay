package flower.popupday.business.dto;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component("statsDTO")
public class StatsDTO {
    private long hits_id;
    private long user_id;
    private long popup_id;
    private Date viewed_at;

    StatsDTO(){};

    StatsDTO(long hits_id, long user_id, long popup_id, Date viewed_at){
        this.hits_id = hits_id;
        this.user_id = user_id;
        this.popup_id = popup_id;
        this.viewed_at = viewed_at;
    };

    public long getHits_id() {
        return hits_id;
    }

    public void setHits_id(long hits_id) {
        this.hits_id = hits_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getPopup_id() {
        return popup_id;
    }

    public void setPopup_id(long popup_id) {
        this.popup_id = popup_id;
    }

    public Date getViewed_at() {
        return viewed_at;
    }

    public void setViewed_at(Date viewed_at) {
        this.viewed_at = viewed_at;
    }
}
