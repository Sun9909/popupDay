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

    @Override
    public void addBusinessLogin(LoginDTO loginDTO) throws DataAccessException {
        System.out.println(loginDTO.toString());
        loginDAO.insertLogin2(loginDTO);
    }

    @Override
    public LoginDTO businessLogin(LoginDTO loginDTO) throws DataAccessException {
        return loginDAO.memberLoginCheck(loginDTO);
    }

    @Override
    public boolean checkId(String user_id) {
        return loginDAO.checkId(user_id);
    }

    @Override
    public boolean checkEmail(String email) {
        return loginDAO.checkEmail(email);
    }

    @Override
    public boolean checkNikname(String user_nikname) {
        return loginDAO.checkNikname(user_nikname);
    }
}
