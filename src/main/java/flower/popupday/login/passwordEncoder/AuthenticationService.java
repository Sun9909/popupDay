package flower.popupday.login.passwordEncoder;

import org.springframework.beans.factory.annotation.Autowired;  // 스프링 프레임워크에서 의존성 주입을 위해 사용
import org.springframework.security.crypto.password.PasswordEncoder;  // 비밀번호 암호화 및 검증을 위한 인터페이스
import org.springframework.stereotype.Service;  // 이 클래스를 서비스 빈으로 등록

@Service
public class AuthenticationService {

    @Autowired
    private PasswordEncoder passwordEncoder;  // PasswordEncoder 빈을 자동으로 주입

    public boolean authenticate(String username, String rawPassword) {
        // 실제로는 데이터베이스에서 유저 정보를 가져와야 해요
        // User user = userRepository.findByUsername(username);
        String storedPasswordHash = "..."; // 유저의 비밀번호 해시

        return passwordEncoder.matches(rawPassword, storedPasswordHash);  // 입력된 비밀번호가 저장된 해시와 일치하는지 확인
    }
}
