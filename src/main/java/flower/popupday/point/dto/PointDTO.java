package flower.popupday.point.dto;

import ch.qos.logback.core.status.Status;
import flower.popupday.login.dto.LoginDTO;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("pointDTO")
public class PointDTO {
    private long shop_id;
    private String product_name;
    private long product_price;
    private long product_count;
    private String image_file_name;
    private Date created_at;
    private Date updated_at;
    private Status status;


    public PointDTO() {};

    public PointDTO(long shop_id, String product_name, long product_price, long product_count,
                    String image_file_name, Date created_at, Date updated_at) {
        this.shop_id = shop_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_count = product_count;
        this.image_file_name = image_file_name;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public enum Status {
        활성, 비활성
    }

    public long getShop_id() {
        return shop_id;
    }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public long getProduct_price() {
        return product_price;
    }

    public void setProduct_price(long product_price) {
        this.product_price = product_price;
    }

    public long getProduct_count() {
        return product_count;
    }

    public void setProduct_count(long product_count) {
        this.product_count = product_count;
    }

    public String getImage_file_name() {
        return image_file_name;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
