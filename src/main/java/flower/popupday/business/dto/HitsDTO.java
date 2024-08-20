package flower.popupday.business.dto;

import org.springframework.stereotype.Component;

@Component("hitsDTO")
public class HitsDTO {
    private String month;
    private int hit_count;

    HitsDTO(){};

    HitsDTO(String month, int hit_count) {
        this.month = month;
        this.hit_count = hit_count;
    }

    public int getHit_count() {
        return hit_count;
    }

    public void setHit_count(int hit_count) {
        this.hit_count = hit_count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
