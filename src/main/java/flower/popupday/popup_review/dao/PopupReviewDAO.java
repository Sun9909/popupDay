package flower.popupday.popup_review.dao;

import flower.popupday.popup_review.dto.PopupReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PopupReviewDAO {

    // 리뷰 삽입
    void insertReview(PopupReviewDTO popupReviewDTO);

    // 특정 팝업 id에 해당하는 리뷰 목록 조회
    List<PopupReviewDTO> selectReviewsByPopupId(long popupId);
}
