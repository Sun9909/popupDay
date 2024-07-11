package flower.popupday;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PopupDayController {

    @GetMapping("/main.do") 
    public ModelAndView main(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        ModelAndView mav=new ModelAndView();
        mav.setViewName("main");
        return mav;
    }
}
