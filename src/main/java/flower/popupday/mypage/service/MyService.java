package flower.popupday.mypage.service;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import flower.popupday.popup.dto.PopupDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface MyService {

    //마이페이지
    public MyDTO getName(MyDTO myDTO) throws DataAccessException;

    //리뷰 개수
    public Long getReviewCount(Long id) throws DataAccessException;

    //문의 개수
    public Long getQnaCount(Long id) throws DataAccessException;

    //포인트
    public Long getPoint(Long id) throws DataAccessException;

    //사용자 정보 조회
    public MyDTO findMember(Long id) throws DataAccessException;

    //사용자 정보 업데이트
    public void updateLogin(LoginDTO loginDTO) throws DataAccessException;

    //이메일 중복 체크
    boolean checkEmail(String email);

    //닉네임 중복 체크
    boolean checknickname(String user_nickname);

    public void updatePwd(LoginDTO loginDTO) throws DataAccessException;

    public void dropMember(LoginDTO loginDTO) throws DataAccessException;

    public Map<String, Object> myPopupLike(Map<String, Integer> pagingMap, Long id) throws DataAccessException;

    public Long getPopupCount(Long user_id) throws DataAccessException;

    public Long getAllPopupCount(Long user_id) throws DataAccessException;

    //내가 쓴 댓글
    public Map commentList(Map<String, Integer> pagingMap, Long id, String filter) throws DataAccessException;

    //내가 쓴 리뷰 보기
    public Map reviewList(Map<String, Integer> pagingMap, Long id) throws DataAccessException;

    //내가 쓴 문의 사항
    public Map qnaList(Map<String, Integer> pagingMap, Long id) throws DataAccessException;

    //찜 삭제
    public void unlikeClick(Long popup_id, Long id) throws DataAccessException;

    public Map businessList(Map<String, Integer> pagingMap, Long id) throws DataAccessException;

    public Map<String, Object> myPopupList(Map<String, Integer> pagingMap, Long id) throws DataAccessException;

    //승인된 팝업 개수
    public int getApprovedPopupCount(int userId) throws DataAccessException;

    public int getRegisterPopupCount(int userId) throws DataAccessException;

    //최근 본 팝업 조회
    public Map<String, Object> getPopupsByIds(List<Long> popupId) throws DataAccessException;
}