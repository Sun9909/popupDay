package flower.popupday.mypage.service;

import flower.popupday.mypage.dto.MyDTO;
import org.springframework.dao.DataAccessException;

public interface MyService {
    public MyDTO getName(MyDTO myDTO) throws DataAccessException;

    public int getReview() throws DataAccessException;

    public Long getReviewCount(Long id) throws DataAccessException;

    public String getreCommentCount(String user_nikname) throws DataAccessException;
    public String getpopCommentCount(String user_nikname) throws DataAccessException;
}
