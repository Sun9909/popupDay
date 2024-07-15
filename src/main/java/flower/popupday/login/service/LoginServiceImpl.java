package flower.popupday.login.service;

import flower.popupday.login.dao.LoginDAO;
import flower.popupday.login.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service("loginService") // 서비스 클래스임을 명시
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDAO loginDAO; // LoginDAO 객체를 자동 주입

    // 일반 회원가입
    @Override
    public void addLogin(LoginDTO loginDTO) throws DataAccessException {
        System.out.println(loginDTO.toString()); // 회원가입 정보를 콘솔에 출력
        //loginDAO.insertLogin(loginDTO);: DAO 객체를 사용하여 회원가입 정보를 데이터베이스에 삽입.
        loginDAO.insertLogin(loginDTO); // 회원가입 DAO 메서드 호출
    }

    // 일반 회원 로그인
    @Override
    public LoginDTO memberLogin(LoginDTO loginDTO) throws DataAccessException {
        // DAO 객체를 사용하여 로그인 정보를 확인하고, 결과를 반환.
        return loginDAO.memberLoginCheck(loginDTO); // 로그인 DAO 메서드 호출
    }

    // 사업자 회원가입
    @Override
    public void addBusinessLogin(LoginDTO loginDTO) throws DataAccessException {
        System.out.println(loginDTO.toString()); // 사업자 회원가입 정보를 콘솔에 출력
        //DAO 객체를 사용하여 사업자 회원가입 정보를 데이터베이스에 삽입.
        loginDAO.insertLogin2(loginDTO); // 사업자 회원가입 DAO 메서드 호출
    }

    // 사업자 회원 로그인
    @Override
    public LoginDTO businessLogin(LoginDTO loginDTO) throws DataAccessException {
        //return loginDAO.memberLoginCheck(loginDTO);: DAO 객체를 사용하여 사업자 로그인 정보를 확인하고, 결과를 반환.
        return loginDAO.memberLoginCheck(loginDTO); // 로그인 DAO 메서드 호출
    }

    // 아이디 중복 확인
    @Override
    public boolean checkId(String user_id) {
        //return loginDAO.checkId(user_id);: DAO 객체를 사용하여 아이디 중복 확인.
        return loginDAO.checkId(user_id); // 아이디 중복 확인 DAO 메서드 호출
    }

    // 이메일 중복 확인
    @Override
    public boolean checkEmail(String email) {
        //return loginDAO.checkEmail(email);: DAO 객체를 사용하여 이메일 중복 확인.
        return loginDAO.checkEmail(email); // 이메일 중복 확인 DAO 메서드 호출
    }

    // 닉네임 중복 확인
    @Override
    public boolean checkNikname(String user_nikname) {
        //return loginDAO.checkNikname(user_nikname);: DAO 객체를 사용하여 닉네임 중복 확인.
        return loginDAO.checkNikname(user_nikname); // 닉네임 중복 확인 DAO 메서드 호출
    }
}
