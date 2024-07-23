package flower.popupday.login.controller;

import flower.popupday.login.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface LoginController {

    // 일반 회원가입
    ModelAndView addLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                          HttpServletRequest request,
                          HttpServletResponse response) throws Exception;

    // 로그인 폼 이동
    String loginForm(HttpServletRequest request, Model model) throws Exception;

    // 로그인.html
    ModelAndView memberLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                             RedirectAttributes rAttr,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception;

    // 사업자 회원가입
    ModelAndView addBusinessLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception;

    // businessForm에서 정보 전달 받기 및 회원가입
    ModelAndView businessLoginForm(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                   @RequestParam(value = "action", required = false) String action,
                                   @RequestParam(value = "result", required = false) String result,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception;

    // businessForm.html 찐 로그인 용
    ModelAndView businessLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                               RedirectAttributes rAttr, // 로그인 되지 않으면 다시 처리
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception;

    // 사람 이미지 클릭시 join.form 이동
    ModelAndView showJoin(HttpServletRequest request,
                          HttpServletResponse response) throws Exception;

    // join에서 choiceForm으로 이동
    ModelAndView showChoiceForm(HttpServletRequest request,
                                HttpServletResponse response) throws Exception;

    // choiceForm에서 businessForm으로 이동
    ModelAndView showBusinessForm(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception;

    // choiceForm에서 memberForm으로 이동
    ModelAndView showMemberForm(HttpServletRequest request,
                                HttpServletResponse response) throws Exception;

    // 아이디 중복 확인
    boolean checkId(String user_id);

    // 이메일 중복 확인
    boolean checkEmail(String email);

    // 닉네임 중복 확인
    boolean checkNikname(String user_nikname);

    // 아이디 탈퇴 확인
    boolean dropCheck(String user_id);

    public ModelAndView removeNotice(@RequestParam("noticeNo") int noticeNo, HttpServletRequest request, HttpServletResponse response)
            throws Exception;

    //로그아웃
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response) throws Exception;

    //카카오 소셜 로그인
    public ModelAndView oauth(
            @RequestParam(value = "code", required = false) String code,
            HttpServletRequest request, HttpServletResponse response) throws Exception;


}
