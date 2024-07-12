package flower.popupday.notice.review.dto;

import org.springframework.stereotype.Component;

@Component("reviewimageDTO")
public class ReviewImageDTO {
    private long review_image_id;
    private long review_id;
    private String image_file_name;

    public long getReview_image_id() {
        return review_image_id;
    }

    public void setReview_image_id(long review_image_id) {
        this.review_image_id = review_image_id;
    }

    public long getReview_id() {
        return review_id;
    }

    public void setReview_id(long review_id) {
        this.review_id = review_id;
    }

    public String getImage_file_name() {
        return image_file_name;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
    }
}
