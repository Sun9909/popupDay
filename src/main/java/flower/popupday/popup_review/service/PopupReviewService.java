package flower.popupday.popup_review.service;

import flower.popupday.popup_review.dto.PopupReviewDTO;

import java.util.List;

public interface PopupReviewService {

    // 리뷰 삽입
    void addReview(PopupReviewDTO popupReviewDTO);

    // 특정 팝업 id에 해당하는 리뷰 목록 조회
    List<PopupReviewDTO> selectReviewsByPopupId(long popup_id);

    //특정 팝업 id에 해당하는 별점 평균
    public double calculateAverageRating(Long popup_id);
}
