package flower.popupday.login.controller;

import flower.popupday.login.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.codehaus.groovy.runtime.powerassert.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface LoginController {

    //일반 회원가입
    public ModelAndView addLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception;

    //login.html에서 정보 전달 받기
    public ModelAndView login(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                              @RequestParam(value = "action", required = false) String action,
                              @RequestParam(value = "result", required = false) String result,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception;

    //로그인.html
    public ModelAndView memberLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                              RedirectAttributes rAttr, //로그인 되지 않으면 다시 처리
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception;

    //사업자 회원가입
    public ModelAndView addBusinessLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception;

    //businessForm에서 정보 전달 받기
    public ModelAndView businessLoginForm(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                          @RequestParam(value = "action", required = false) String action,
                                          @RequestParam(value = "result", required = false) String result,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws Exception;

    //businessForm.html
    public ModelAndView businessLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                    RedirectAttributes rAttr, //로그인 되지 않으면 다시 처리
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception;


}
