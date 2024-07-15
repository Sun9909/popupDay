package flower.popupday.login.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("loginController")
public class LoginControllerImpl implements LoginController {

    @Autowired //자동주입
    private LoginService loginService;

    //회원가입
    @Override
    @PostMapping("/login/addLogin.do")
    public ModelAndView addLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        loginService.addLogin(loginDTO);
        ModelAndView mav = new ModelAndView("redirect:/main.do");
        return mav;
    }

    //login.html에서 정보를 전달용
    @Override
    @GetMapping("/login/login.do")
    public ModelAndView login(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                              @RequestParam(value = "action", required = false) String action,
                              @RequestParam(value = "result", required = false) String result,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("action", action);
        ModelAndView mav = new ModelAndView();
        mav.addObject("result", result);
        mav.setViewName("/login/login");
        return mav;
    }

    //login.html에서 찐 로그인용
    @Override
    @PostMapping("/login/log.do")
    public ModelAndView memberLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                    RedirectAttributes rAttr,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        loginDTO = loginService.memberLogin(loginDTO);
        ModelAndView mav = new ModelAndView();
        if (loginDTO != null) { //회원 정보가 있는 경우
            HttpSession session = request.getSession();
            session.setAttribute("loginDTO", loginDTO);
            session.setAttribute("isLogOn", true);
            String action = (String) session.getAttribute("action");
            if (action != null) {
                mav.setViewName("redirect:" + action); //로그인 진행한 위치로 돌아감
            } else {
                mav.setViewName("redirect:/main.do");
            }
        } else { //회원정보가 없는 경우
            rAttr.addFlashAttribute("result", "아이디나 비밀번호를 다시 입력해주세요");
            mav.setViewName("redirect:/login/login.do");
        }
        return mav;
    }

    //사업자 회원가입
    @Override
    @PostMapping("/login/addBusinessLogin.do")
    public ModelAndView addBusinessLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        loginService.addBusinessLogin(loginDTO);
        ModelAndView mav = new ModelAndView("redirect:/main.do");
        return mav;
    }


    //businessForm.html에서 정보 전달용
    @Override
    @PostMapping("login/businessForm.do")
    public ModelAndView businessLoginForm(@ModelAttribute("loginDTO") LoginDTO loginDTO,
            @RequestParam(value = "action", required = false) String action,
                                          @RequestParam(value = "result", required = false) String result,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("action", action);
        ModelAndView mav = new ModelAndView();
        mav.addObject("result", result);
        mav.setViewName("/login/businessForm");
        return mav;
    }

    //businessForm.html 찐 로그인 용
    @Override
    @PostMapping("login/businessLog.do")
    public ModelAndView businessLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                      RedirectAttributes rAttr,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        loginDTO = loginService.memberLogin(loginDTO);
        ModelAndView mav = new ModelAndView();
        if (loginDTO != null) { //회원 정보가 있는 경우
            HttpSession session = request.getSession();
            session.setAttribute("loginDTO", loginDTO);
            session.setAttribute("isLogOn", true);
            String action = (String) session.getAttribute("action");
            if (action != null) {
                mav.setViewName("redirect:" + action); //로그인 진행한 위치로 돌아감
            } else {
                mav.setViewName("redirect:/main.do");
            }
        } else { //회원정보가 없는 경우
            rAttr.addFlashAttribute("result", "아이디나 비밀번호를 다시 입력해주세요");
            mav.setViewName("redirect:/login/businessForm.do");
        }
        return mav;
    }


    @Override
    @GetMapping("/login/join.do")
    public ModelAndView showjoin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("/login/join");
        return modelAndView;
    }


    @Override
    @GetMapping("/login/choiceForm.do")
    public ModelAndView showChoiceForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("/login/choiceForm");
        return modelAndView;
    }

    @Override
    @GetMapping("/login/businessForm.do")
    public ModelAndView showbusinessForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("/login/businessForm");
        return modelAndView;
    }

    @Override
    @GetMapping("/login/memberForm.do")
    public ModelAndView showmemberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("/login/memberForm");
        return modelAndView;
    }


}