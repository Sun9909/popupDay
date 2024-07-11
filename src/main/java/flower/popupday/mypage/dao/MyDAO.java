package flower.popupday.mypage.dao;

import flower.popupday.mypage.dto.MyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

@Mapper
public interface MyDAO {
    public MyDTO getName(MyDTO myDTO) throws DataAccessException;

    public int selectAllReview() throws DataAccessException;

    public Long getReviewCount(Long id) throws DataAccessException;

    public String getreCommentCount(String user_nikname) throws DataAccessException;
    public String getpopCommentCount(String user_nikname) throws DataAccessException;
}
