package flower.popupday.login.passwordEncoder;

import flower.popupday.login.handler.CustomAuthenticationFailureHandler;
import flower.popupday.login.handler.CustomAuthenticationSuccessHandler;
import flower.popupday.security.RequestSaveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 이 클래스가 Spring의 설정 클래스임을 나타냅니다.
@EnableWebSecurity // Spring Security의 웹 보안을 활성화합니다.
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final RequestSaveFilter requestSaveFilter;

    // 생성자 주입을 통해 필요한 핸들러와 필터를 초기화합니다.
    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                          CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
                          RequestSaveFilter requestSaveFilter) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.requestSaveFilter = requestSaveFilter;
    }

    @Bean // 이 메서드가 반환하는 객체를 Spring 컨텍스트의 빈으로 등록
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> // HTTP 요청에 대한 인가 설정을 시작
                        authorizeRequests
                                .anyRequest().permitAll() // 모든 요청을 허용
                )
                .formLogin(formLogin -> // 폼 기반 로그인을 구성
                        formLogin
                                .loginPage("/login/login.do") // 로그인 페이지 URL을 설정
                                .successHandler(customAuthenticationSuccessHandler) // 로그인 성공 시 사용할 핸들러를 설정
                                .failureHandler(customAuthenticationFailureHandler) // 로그인 실패 시 사용할 핸들러를 설정
                                .permitAll() // 로그인 페이지에 대한 접근을 허용
                )
                .logout(logout -> // 로그아웃을 구성
                        logout
                                .logoutUrl("/logout") // 로그아웃 URL을 설정
                                .logoutSuccessUrl("/login?logout") // 로그아웃 성공 시 리디렉션할 URL을 설정
                                .permitAll() // 로그아웃 URL에 대한 접근을 허용
                )
                .csrf(csrf -> csrf.disable()) // CSRF 보호를 비활성화
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // 동일 출처의 프레임만 허용
                .addFilterBefore(requestSaveFilter, UsernamePasswordAuthenticationFilter.class); // 사용자 이름/암호 인증 필터 앞에 RequestSaveFilter를 추가

        return http.build(); // SecurityFilterChain을 빌드하여 반환
    }

    //@Bean 애너테이션을 사용하면 특정 클래스의 인스턴스를 Spring 컨테이너가 자동으로 생성하고 관리하도록 할 수 있음, 이를 통해 애플리케이션에서 객체 간의 의존성을 쉽게 주입 가능
    @Bean // 이 메서드가 반환하는 객체를 Spring 컨텍스트의 빈으로 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화를 위한 BCryptPasswordEncoder를 반환
    }
}
