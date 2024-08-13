package flower.popupday.popup_review.service;

import flower.popupday.popup_review.dao.PopupReviewDAO;
import flower.popupday.popup_review.dto.PopupReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopupReviewServiceImpl implements PopupReviewService {

    @Autowired
    private PopupReviewDAO popupReviewDAO;


    @Override
    public void addReview(PopupReviewDTO popupReviewDTO) {
        popupReviewDAO.addReview(popupReviewDTO);
    }

    @Override
    public List<PopupReviewDTO> selectReviewsByPopupId(long popup_id) {
        List<PopupReviewDTO> reviews = popupReviewDAO.selectReviewsByPopupId(popup_id);
        System.out.println("Reviews from DAO: " + reviews);
        return reviews;
    }
}
