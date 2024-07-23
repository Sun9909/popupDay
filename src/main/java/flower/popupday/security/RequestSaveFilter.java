package flower.popupday.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // 이 어노테이션은 이 클래스가 Spring의 컴포넌트 스캔에 의해 관리되는 빈임을 나타냄
public class RequestSaveFilter implements Filter {

    @Override
    public void doFilter(jakarta.servlet.ServletRequest servletRequest, jakarta.servlet.ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        // HttpServletRequest와 HttpServletResponse로 형 변환
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 요청 URI가 로그인 페이지(/login/login.do)와 같을 경우 이전 페이지의 URL을 세션에 저장
        if (request.getRequestURI().equals("/login/login.do")) {
            // HTTP Referer 헤더에서 이전 페이지의 URL을 가져옴
            String referer = request.getHeader("Referer");
            // Referer가 null이 아니고 로그인 페이지가 아닌 경우에만 세션에 저장
            if (referer != null && !referer.contains("/login/login.do")) {
                request.getSession().setAttribute("action", referer); // 세션에 'action'이라는 이름으로 이전 페이지 URL을 저장
            }
        }
        // 다음 필터 또는 최종 목적지로 요청과 응답을 전달
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 메서드로, 특별한 초기화 작업이 필요하지 않으므로 구현하지 않습니다.
    }

    @Override
    public void destroy() {
        // 필터 종료 메서드로, 특별한 종료 작업이 필요하지 않으므로 구현하지 않습니다.
    }
}
