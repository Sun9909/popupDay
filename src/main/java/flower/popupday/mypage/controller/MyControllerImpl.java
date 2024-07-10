package flower.popupday.mypage.controller;

import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.service.MyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller("myController")
public class MyControllerImpl implements MyController {

    @Autowired
    private MyService myService;

    @Autowired
    private MyDTO myDTO;

    @Override
    @RequestMapping("/mypage/memberPage.do") //
    public ModelAndView getName(@ModelAttribute("myDTO") MyDTO myDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        myDTO=myService.getName(myDTO);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("mypage/memberPage");
        mav.addObject("my",myDTO);
        return mav;
    }
}
