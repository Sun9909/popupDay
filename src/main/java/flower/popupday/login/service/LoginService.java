package flower.popupday.login.service;

import flower.popupday.login.dto.LoginDTO;
import org.springframework.dao.DataAccessException;

public interface LoginService {

    //회원 가입 메서드
    public void addLogin(LoginDTO loginDTO) throws DataAccessException;

    //login.html에서 찐 로그인용
    public LoginDTO memberLogin(LoginDTO loginDTO) throws DataAccessException;

    //회원 가입 메서드
    public void addBusinessLogin(LoginDTO loginDTO) throws DataAccessException;

    //businessForm.html에서 찐 로그인용
    public LoginDTO businessLogin(LoginDTO loginDTO) throws DataAccessException;

    // 아이디 중복 확인
    boolean checkId(String user_id);

    // 이메일 중복 확인
    boolean checkEmail(String email);

    // 닉네임 중복 확인
    boolean checkNikname(String user_nikname);


}
