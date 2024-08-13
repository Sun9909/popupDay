package flower.popupday.mypage.dao;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

@Mapper
//@Repository("myDAO")
public interface MyDAO {
    public MyDTO getName(MyDTO myDTO) throws DataAccessException;

    public Long getReviewCount(Long id) throws DataAccessException;

    public Long getQnaCount(Long id) throws DataAccessException;

    public MyDTO selectMemberById(Long id) throws DataAccessException;

    public void updateLogin(LoginDTO loginDTO) throws DataAccessException;

    public boolean checkEmail(String email) throws DataAccessException;

    public boolean checknickname(String user_nickname) throws DataAccessException;

    public void updatePwd(LoginDTO loginDTO) throws DataAccessException;

    //회원 삭제
    public void dropMember(LoginDTO loginDTO) throws DataAccessException;

    public List selectMyPopup(@Param("count") int count, @Param("id") Long id) throws DataAccessException;

    public List selectMyLikePopup(@Param("count") int count, @Param("id") Long id) throws DataAccessException;

    public Long selectToPopup(@Param("id") Long id) throws DataAccessException;

    public ImageDTO selectFirstImage(Long popup_id) throws DataAccessException;

    public Long getPopupCount(Long user_id) throws DataAccessException;

    public Long getAllPopupCount(Long user_id) throws DataAccessException;

    //내가 쓴 리뷰
    public List selectAllReview(@Param("count") int count, @Param("id") int id) throws DataAccessException;

    public int selectToReview(@Param("id") int id) throws DataAccessException;

    //내가 쓴 문의 사항
    public List selectAllQnaList(@Param("count") int count, @Param("id") int id) throws DataAccessException;

    public int selectToQna(@Param("id") int id) throws DataAccessException;

    //찜 삭제
    public void unlikeClick(Long popup_id,Long id) throws DataAccessException;

    public int selectTooPopup(int userId);

    public int selectTotPopup(int userId);

    public List<String> selectPopupTags(Long popupId);

    public ImageDTO selectFirstImg(Long popupId);

    public List<PopupDTO> selectBusinessPopup(int count, Long id);

    public int selectToBusinessPopup(Long id);

    public int selectToMyPopup(Long id);

    public Map<String, Object> findPopupById(Long popup_id) throws DataAccessException;
}