package flower.popupday.login.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

//로그인 성공 후, 세션에 저장된 이전 페이지로 리다이렉트하도록 하는 핸들러
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String redirectUrl = (String) session.getAttribute("action");
        if (redirectUrl != null) {
            session.removeAttribute("action");
            response.sendRedirect(redirectUrl);
        } else {
            response.sendRedirect("/main.do");
        }
    }

}
