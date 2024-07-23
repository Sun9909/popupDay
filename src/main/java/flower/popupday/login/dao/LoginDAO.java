package flower.popupday.login.dao;

import flower.popupday.login.dto.LoginDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Mapper // mapper로 LoginDAO와 연결
public interface LoginDAO {

    // 일반 회원 로그인.xml
    void insertLogin(LoginDTO loginDTO) throws DataAccessException;

    // 회원정보가 있는지 로그인 할 때 확인하는 부분
    LoginDTO memberLoginCheck(LoginDTO loginDTO) throws DataAccessException;

    // 사업자 회원 로그인.xml
    void insertLogin2(LoginDTO loginDTO) throws DataAccessException;

    // 아이디 중복 확인
    boolean checkId(String user_id) throws DataAccessException;

    // 이메일 중복 확인
    boolean checkEmail(String email) throws DataAccessException;

    // 닉네임 중복 확인
    boolean checkNikname(String user_nikname) throws DataAccessException;

    // 회원 탈퇴 확인
    public String getStatus(String user_id) throws DataAccessException;

    //카카오
    public void kakaoInsert(LoginDTO loginDTO) throws DataAccessException;

    public boolean isExistKakao(LoginDTO loginDTO) throws DataAccessException;

    //카카오 소셜 로그인
    public void kakaoUpdate (LoginDTO loginDTO) throws DataAccessException;

    // 사용자 정보 조회
    LoginDTO findByUserId(String userId) throws DataAccessException;

}
