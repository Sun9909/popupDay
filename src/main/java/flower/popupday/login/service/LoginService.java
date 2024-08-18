package flower.popupday.login.service;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.login.dto.LoginHashTagDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface LoginService {

    // 회원 가입 메서드
    public void addLogin(LoginDTO loginDTO, List<Long> hash_tag_id) throws DataAccessException;
    // 주어진 회원 정보를 사용하여 회원 가입을 처리하는 메서드

    // login.html에서 찐 로그인용
    public LoginDTO memberLogin(LoginDTO loginDTO) throws DataAccessException;
    // 주어진 로그인 정보를 사용하여 회원 로그인 처리를 하는 메서드

    // 회원 가입 메서드 (사업자용)
    public void addBusinessLogin(LoginDTO loginDTO) throws DataAccessException;
    // 주어진 사업자 회원 정보를 사용하여 사업자 회원 가입을 처리하는 메서드

    // businessForm.html에서 찐 로그인용 (사업자용)
    public LoginDTO businessLogin(LoginDTO loginDTO) throws DataAccessException;
    // 주어진 사업자 로그인 정보를 사용하여 사업자 회원 로그인 처리를 하는 메서드

    // 아이디 중복 확인
    boolean checkId(String user_id);
    // 주어진 아이디의 중복 여부를 확인하는 메서드
    //String 타입의 user_id를 매개변수로 받아서 해당 아이디가 이미 존재하는지 확인하고, 결과를 반환.

    // 이메일 중복 확인
    boolean checkEmail(String email);
    // 주어진 이메일의 중복 여부를 확인하는 메서드
    //String 타입의 email을 매개변수로 받아서 해당 이메일이 이미 존재하는지 확인하고, 결과를 반환.


    // 닉네임 중복 확인
    boolean checknickname(String user_nickname);
    // 주어진 닉네임의 중복 여부를 확인하는 메서드
    // String 타입의 user_nickname을 매개변수로 받아서 해당 닉네임이 이미 존재하는지 확인하고, 결과를 반환.

    //회원 탈퇴 확인
    boolean dropCheck(String user_id);

    //카카오 소셜 로그인
    public String getKakaoAccessToken(String code) throws Exception;

    public LoginDTO getKakaoUserInfo(String accessToken) throws Exception;

    public void kakaoLogin(LoginDTO loginDTO) throws Exception;

    //해시태그 조회
    public List<LoginHashTagDTO> hashtagList() throws Exception;
}