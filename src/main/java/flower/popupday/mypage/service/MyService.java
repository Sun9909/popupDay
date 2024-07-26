package flower.popupday.mypage.service;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import flower.popupday.popup.dto.PopupDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface MyService {
//    public LoginDTO getname(LoginDTO loginDTO) throws DataAccessException;
//    public MyDTO getMyDTO(LoginDTO loginDTo) throws DataAccessException;

    //마이페이지
    public MyDTO getName(MyDTO myDTO) throws DataAccessException;

    public int getReview() throws DataAccessException;

    public Long getReviewCount(Long id) throws DataAccessException;

    //public String getreCommentCount(String user_nickname) throws DataAccessException;
    //public String getpopCommentCount(String user_nickname) throws DataAccessException;

    public Long getQnaCount(Long id) throws DataAccessException;

    //사용자 정보 조회
    public MyDTO findMember(Long id) throws DataAccessException;

    //사용자 정보 업데이트
    public void updateLogin(LoginDTO loginDTO) throws DataAccessException;    //myDTO를 사용해야하나? 로그인 정보가 바뀌어야 하니까?

    //이메일 중복 체크
    boolean checkEmail(String email);

    //닉네임 중복 체크
    boolean checknickname(String user_nickname);

    public void updatePwd(LoginDTO loginDTO) throws DataAccessException;

    public void dropMember(LoginDTO loginDTO) throws DataAccessException;

    public Map<String, Object> myPopupLike(Map<String, Integer> pagingMap) throws DataAccessException;

    public Long getPopupCount(Long user_id) throws DataAccessException;
}