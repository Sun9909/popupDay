package flower.popupday.popup_comment.service;

import flower.popupday.popup_comment.dto.PopupCommentDTO;

import java.util.List;

public interface PopupCommentService {

    // 리뷰 삽입
    void addComment(PopupCommentDTO popupCommentDTO);

    // 특정 팝업 id에 해당하는 리뷰 목록 조회
    List<PopupCommentDTO> selectCommentsByPopupId(long popupId);

    //특정 팝업 id에 해당하는 별점 평균
    public double calculateAverageRating(Long popupId);


    public PopupCommentDTO getCommentById(Long userId);

    //리뷰 삭제
    void deleteComment(Long popupCommentId) throws Exception;

    //리뷰 수정
    public void updateComment(Long popupCommentId, Long popupId, String content, int rating);
}
