package flower.popupday;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PopupController {

    @GetMapping("/popupList.do") // (매핑이름) ex : ("/popupList.do)
    public ModelAndView popupList(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        ModelAndView mav=new ModelAndView();
        mav.setViewName("/mypage/popupList"); // "/폴더이름/파일이름 " ex ) ("/mypage/popupList")
        return mav;
    }
}
