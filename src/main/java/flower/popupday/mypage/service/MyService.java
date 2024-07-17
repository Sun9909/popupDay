package flower.popupday.mypage.service;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface MyService {
//    public LoginDTO getname(LoginDTO loginDTO) throws DataAccessException;
//    public MyDTO getMyDTO(LoginDTO loginDTo) throws DataAccessException;

    //마이페이지
    public MyDTO getName(MyDTO myDTO) throws DataAccessException;

    public int getReview() throws DataAccessException;

    public Long getReviewCount(Long id) throws DataAccessException;

    public String getreCommentCount(String user_nikname) throws DataAccessException;
    public String getpopCommentCount(String user_nikname) throws DataAccessException;

    public Long getQnaCount(Long id) throws DataAccessException;

    //수정
    public MyDTO findMember(Long id) throws DataAccessException;

    //팝업 리스트
    //public MyPopupDTO getPopup(MyPopupDTO mypopupDTO) throws DataAccessException;
    public List<MyPopupDTO> getPopup(String user_id) throws DataAccessException;

    public Long getPopupCount(Long user_id) throws DataAccessException;
}
