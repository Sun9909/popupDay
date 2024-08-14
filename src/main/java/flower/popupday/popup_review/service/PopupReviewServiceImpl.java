package flower.popupday.popup_review.service;

import flower.popupday.popup_review.dao.PopupReviewDAO;
import flower.popupday.popup_review.dto.PopupReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PopupReviewServiceImpl implements PopupReviewService {

    @Autowired
    private PopupReviewDAO popupReviewDAO;

    private static final Logger logger = LoggerFactory.getLogger(PopupReviewServiceImpl.class);



    @Override
    public void addReview(PopupReviewDTO popupReviewDTO) {
        popupReviewDAO.addReview(popupReviewDTO);
    }

    @Override
    public List<PopupReviewDTO> selectReviewsByPopupId(long popup_id) {
        try {
            List<PopupReviewDTO> reviews = popupReviewDAO.selectReviewsByPopupId(popup_id);
            if (reviews != null) {
                logger.debug("Reviews from DAO: {}", reviews);
            } else {
                logger.debug("No reviews found or DAO returned null.");
            }
            return reviews;
        } catch (Exception e) {
            logger.error("Exception occurred while fetching reviews: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    //특정 팝업 id에 해당하는 별점 평균
    public double calculateAverageRating(Long popup_id) {
        return popupReviewDAO.getAverageRatingByPopupId(popup_id);
    }
}
