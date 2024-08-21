package flower.popupday.mypage.dto;

public class MyGifticonDTO {

    private Long gifticon_id;
    private Long user_id;
    private Long shop_id;
    private String image_file_name;

    public Long getGifticon_id() {
        return gifticon_id;
    }

    public void setGifticon_id(Long gifticon_id) {
        this.gifticon_id = gifticon_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getShop_id() {
        return shop_id;
    }

    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    public String getImage_file_name() {
        return image_file_name;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
    }
}
