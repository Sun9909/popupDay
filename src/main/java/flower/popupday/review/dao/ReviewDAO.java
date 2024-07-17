package flower.popupday.review.dao;

import flower.popupday.review.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewDAO {
    public List selectAllReview(@Param("count") int count) throws DataAccessException;

    public int getNewReviewId() throws DataAccessException;

    public void insertNewReview(Map articleMap) throws DataAccessException;

    public void insertNewImages(Map articleMap) throws DataAccessException;

    public int selectToReview() throws DataAccessException;

    //상세 글 보기
    public ReviewDTO selectReview(int review_id) throws DataAccessException;

    //상세 글 이미지 보기
    public List selectImageList(int review_id) throws DataAccessException;

    public void updateReview(Map reviewMap) throws DataAccessException;

    public void updateImage(Map reviewMap) throws DataAccessException;

    public void deleteReview(int review_id) throws DataAccessException;

    public void deleteImage(int review_id) throws DataAccessException;
}
