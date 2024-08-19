package flower.popupday.notice.review.dao;

import flower.popupday.notice.review.dto.ReviewCommentDTO;
import flower.popupday.notice.review.dto.ReviewDTO;
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
}
