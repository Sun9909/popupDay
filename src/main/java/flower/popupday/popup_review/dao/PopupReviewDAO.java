package flower.popupday.popup_review.dao;

import flower.popupday.popup_review.dto.PopupReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface PopupReviewDAO {

    // 리뷰 삽입
    void addReview(PopupReviewDTO popupReviewDTO);

    // 특정 팝업 id에 해당하는 리뷰 목록 조회
    List<PopupReviewDTO> selectReviewsByPopupId(long popup_id);

    //특정 팝업 id에 해당하는 별점 평균
    public double getAverageRatingByPopupId(Long popup_id);

    //리뷰 수정
    void updateReviewContent(Long userId, String content, int rating);

    //수정을 위해서 리뷰 찾아주는 쿼리
    PopupReviewDTO findById(Long reviewId);
}
