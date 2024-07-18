package flower.popupday.mypage.dao;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
//@Repository("myDAO")
public interface MyDAO {
    public MyDTO getName(MyDTO myDTO) throws DataAccessException;

    public int selectAllReview() throws DataAccessException;

    public Long getReviewCount(Long id) throws DataAccessException;

    public String getreCommentCount(String user_nikname) throws DataAccessException;
    public String getpopCommentCount(String user_nikname) throws DataAccessException;

    public Long getQnaCount(Long id) throws DataAccessException;

    public MyDTO selectMemberById(Long id) throws DataAccessException;

    public void updateLogin(LoginDTO loginDTO) throws DataAccessException;

    //public boolean checkNikname(String user_nikname) throws DataAccessException;

    //public MyPopupDTO getPopup(MyPopupDTO mypopupDTO) throws DataAccessException;
    public List<MyPopupDTO> getPopup(String user_id) throws DataAccessException;

    public Long getPopupCount(Long user_id) throws DataAccessException;
}
