package flower.popupday.notice.review.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewDAO {
    public List selectAllReview() throws DataAccessException;

    public int getNewReviewId() throws DataAccessException;

    public void insertNewReview(Map articleMap) throws DataAccessException;

    public void insertNewImages(Map articleMap) throws DataAccessException;

}
