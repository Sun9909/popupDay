package flower.popupday.login.passwordEncoder;

import org.springframework.beans.factory.annotation.Autowired;  // 스프링 프레임워크에서 의존성 주입을 위해 사용
import org.springframework.security.crypto.password.PasswordEncoder;  // 비밀번호 암호화 및 검증을 위한 인터페이스
import org.springframework.stereotype.Service;  // 이 클래스를 서비스 빈으로 등록

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;  // PasswordEncoder 빈을 자동으로 주입

    public void registerUser(String username, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);  // 입력된 비밀번호를 암호화
        // 여기에 유저 정보를 데이터베이스에 저장
        // userRepository.save(new User(username, encodedPassword));
    }
}
