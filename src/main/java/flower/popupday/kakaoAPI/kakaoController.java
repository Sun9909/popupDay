package flower.popupday.kakaoAPI;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class kakaoController {

    //카카오 로그인 기능이 처리되는 페이지
    @RequestMapping(value = "/auth/kakao/login")
    public @ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {

        String reqUrl =
                "https://kauth.kakao.com/oauth/authorize?client_id=c9237e3260b74eea6991180ceca971ca&redirect_uri=http://127.0.0.1:8090/auth/kakao/login&response_type=code";

        return reqUrl;
    }

}