package flower.popupday.notice.review.dao;

import flower.popupday.notice.review.dto.ReviewCommentDTO;
import flower.popupday.notice.review.dto.ReviewDTO;
import flower.popupday.popup_comment.dto.PopupCommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewDAO {
    public List selectAllReview(@Param("count") int count) throws DataAccessException;

    public int getNewReviewId() throws DataAccessException;

    public void insertNewReview(Map reviewMap) throws DataAccessException;

    public void insertReviewPoint(Map reviewMap) throws DataAccessException;

    public void updateReviewPoint(Map reviewMap) throws DataAccessException;

    public void insertNewImages(Map reviewMap) throws DataAccessException;

    public int selectToReview() throws DataAccessException;

    public ReviewDTO selectReview(int review_id) throws DataAccessException;

    public List selectImageList(int review_id) throws DataAccessException;

    public void updateReview(Map reviewMap) throws DataAccessException;

    public void updateImage(Map reviewMap) throws DataAccessException;

    public void deleteReview(int review_id) throws DataAccessException;

    public void deleteImage(int review_id) throws DataAccessException;

    public void addReviewComment(ReviewCommentDTO reviewComment) throws DataAccessException;

    public List <ReviewCommentDTO> selectReviewComment(int review_id) throws DataAccessException;

    public void deleteComment(Long reviewCommentId) throws DataAccessException;

    public void updateComment(Long reviewCommentId, Long reviewId, String content) throws DataAccessException;

    // 특정 팝업 id에 해당하는 리뷰 목록 조회
    List<PopupCommentDTO> selectCommentsByPopupId(long popupId);

    // 회원 id에 해당하는 리뷰 목록 조회
    List<ReviewCommentDTO> selectReviewByUserId(long id);
}
