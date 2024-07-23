package flower.popupday;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PopupDayController {


    // /main.do 경로 요청이 들어오면 main 뷰를 반환
    @GetMapping("/main.do")
    public ModelAndView main(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        // main 뷰를 설정 (main.html 또는 main.jsp 파일이 있어야 함)
        mav.setViewName("main");
        return mav;
    }

    @GetMapping("/main2.do")
    public ModelAndView main2(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        // main 뷰를 설정 (main.html 또는 main.jsp 파일이 있어야 함)
        mav.setViewName("/admin/admin");
        return mav;
    }
}
