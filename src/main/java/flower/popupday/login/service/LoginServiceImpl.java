package flower.popupday.login.service;

import flower.popupday.login.dao.LoginDAO;
import flower.popupday.login.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDAO loginDAO;


    @Override
    public void addLogin(LoginDTO loginDTO) throws DataAccessException {
        System.out.println(loginDTO.toString());
        loginDAO.insertLogin(loginDTO);
    }

    @Override
    public LoginDTO memberLogin(LoginDTO loginDTO) throws DataAccessException {
        return loginDAO.memberLoginCheck(loginDTO);
    }
}