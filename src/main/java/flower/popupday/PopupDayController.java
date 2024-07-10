package flower.popupday;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PopupDayController {

    @GetMapping("/main.do") // (매핑이름)
    public ModelAndView main(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        ModelAndView mav=new ModelAndView();
        // "/폴더이름/파일이름 " ex ) ("/mypage/popupList")
        mav.setViewName("board/popupForm");
        return mav;
    }

























}
