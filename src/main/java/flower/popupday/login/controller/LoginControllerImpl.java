package flower.popupday.login.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("loginController")
public class LoginControllerImpl implements LoginController {

    @Autowired //자동주입
    private LoginService loginService;

    @Autowired
    private LoginDTO loginDTO;

    //회원가입
    @Override
    @PostMapping("/login/addLogin.do")
    public ModelAndView addLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        loginService.addLogin(loginDTO);
        ModelAndView mav=new ModelAndView("redirect:/main.do");
        return mav;
    }
}