package flower.popupday.popup_comment.dao;

import flower.popupday.popup_comment.dto.PopupCommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PopupCommentDAO {

    // 리뷰 삽입
    void addComment(PopupCommentDTO popupCommentDTO);

    // 특정 팝업 id에 해당하는 리뷰 목록 조회
    List<PopupCommentDTO> selectCommentsByPopupId(long popupId);

    //특정 팝업 id에 해당하는 별점 평균
    public double getAverageRatingByPopupId(Long popupId);

    //리뷰 수정
    void updateCommentContent(Long userId, String content, int rating);

    //수정을 위해서 리뷰 찾아주는 쿼리
    PopupCommentDTO findById(Long userId);

    //리뷰 삭제
    void deleteComment(Long popupCommentId) throws Exception;

    //리뷰 수정
    void updateComment(Long popupCommentId, Long popupId, String content, int rating);
}
