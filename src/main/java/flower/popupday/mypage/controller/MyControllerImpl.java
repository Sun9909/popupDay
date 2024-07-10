package flower.popupday.mypage.controller;

import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.service.MyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller("myController")
public class MyControllerImpl implements MyController {

    @Autowired
    private MyService myService;

    @Autowired
    private MyDTO myDTO;

    @PostMapping("/mypage/memberPage.do")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List membersList = myService.membersList();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/mypage/memberPage");
        mav.addObject("membersList", membersList);
        return mav;

    }
}
