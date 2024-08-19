package flower.popupday.popup_comment.service;

import flower.popupday.popup_comment.dao.PopupCommentDAO;
import flower.popupday.popup_comment.dto.PopupCommentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PopupCommentServiceImpl implements PopupCommentService {

    @Autowired
    private PopupCommentDAO popupCommentDAO;

    private static final Logger logger = LoggerFactory.getLogger(PopupCommentServiceImpl.class);



    @Override
    public void addComment(PopupCommentDTO popupCommentDTO) {
        popupCommentDAO.addComment(popupCommentDTO);
    }

    @Override
    public List<PopupCommentDTO> selectCommentsByPopupId(long popupId) {
        try {
            List<PopupCommentDTO> comments = popupCommentDAO.selectCommentsByPopupId(popupId);
            if (comments != null) {
                logger.debug("Comments from DAO: {}", comments);
            } else {
                logger.debug("No comments found or DAO returned null.");
            }
            return comments;
        } catch (Exception e) {
            logger.error("Exception occurred while fetching comments: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    //특정 팝업 id에 해당하는 별점 평균
    public double calculateAverageRating(Long popupId) {
        return popupCommentDAO.getAverageRatingByPopupId(popupId);
    }

    // 회원 id에 해당하는 리뷰 목록 조회
    public List<PopupCommentDTO> selectCommentsByUserId(long id) {
        return popupCommentDAO.selectCommentsByUserId(id);
    }


    // 특정 리뷰 ID로 리뷰를 가져오는 메서드
    public PopupCommentDTO getCommentById(Long userId) {
        return popupCommentDAO.findById(userId);  // 직접 객체 반환
    }

    //리뷰삭제
    @Override
    public void deleteComment(Long popupCommentId) throws Exception {
        popupCommentDAO.deleteComment(popupCommentId);
    }

    //리뷰 수정
    public void updateComment(Long popupCommentId, Long popupId, String content, int rating) {
        popupCommentDAO.updateComment(popupCommentId, popupId, content, rating);
    }
}
