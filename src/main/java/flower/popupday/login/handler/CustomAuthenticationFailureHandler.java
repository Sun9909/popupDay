package flower.popupday.login.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

//로그인 실패 시 세션에 오류 메시지를 설정하는 핸들러
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // 세션을 가져옵니다.
        HttpSession session = request.getSession();
        // 로그인 실패 시 표시할 오류 메시지를 세션에 저장합니다.
        session.setAttribute("errorMessage", "로그인 및 비밀번호가 일치하지 않습니다.");
        // 기본 로그인 페이지로 리다이렉트 설정합니다.
        super.setDefaultFailureUrl("/login/login.do");
        // 부모 클래스의 실패 처리 메서드를 호출합니다.
        super.onAuthenticationFailure(request, response, exception);
    }
}