package flower.popupday.login.service;

import flower.popupday.login.dto.LoginDTO;
import org.springframework.dao.DataAccessException;

public interface LoginService {

    //회원 가입 메서드
    public void addLogin(LoginDTO loginDTO) throws DataAccessException;

    //로그인.html
    public LoginDTO memberLogin(LoginDTO loginDTO) throws DataAccessException;
}
