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

@Controller
@RequestMapping("/login")
public class LoginControllerImpl implements LoginController {

    @Autowired
    private LoginService loginService; // LoginService 객체를 자동 주입

    @Autowired
    private LoginDTO loginDTO;

    // 일반 회원가입
    @Override
    @PostMapping("/addLogin.do")
    public ModelAndView addLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8"); // 요청의 인코딩 설정
        loginService.addLogin(loginDTO); // 회원가입 서비스 호출
        return new ModelAndView("redirect:/main.do"); // 회원가입 완료 후 메인 페이지로 리다이렉트
    }

    // 로그인 폼 이동
    @Override
    @RequestMapping ("/login.do")
    public ModelAndView login(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                              @RequestParam(value = "action", required = false) String action,
                              @RequestParam(value = "result", required = false) String result,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); // 세션을 가져옴
        session.setAttribute("action", action); // 세션에 action 속성 설정
        ModelAndView mav = new ModelAndView(); // ModelAndView 객체 생성
        mav.addObject("result", result); // 모델에 result 속성 추가
        mav.setViewName("/login/login"); // 뷰 이름 설정
        return mav; // ModelAndView 반환
    }

    // 로그인 값저장
    @Override
    @PostMapping("/log.do")
    public ModelAndView memberLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                    RedirectAttributes rAttr,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        LoginDTO loginResult = loginService.memberLogin(loginDTO);
        ModelAndView mav = new ModelAndView();
        if (loginResult != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginDTO", loginResult);
            session.setAttribute("isLogOn", true);
            String action = (String) session.getAttribute("action");
            if (action != null) {
                mav.setViewName("redirect:" + action);
            } else {
                mav.setViewName("redirect:/main.do");
            }
        } else {
            rAttr.addFlashAttribute("result", "아이디나 비밀번호를 다시 입력해주세요");
            mav.setViewName("redirect:/login/login.do");
        }
        return mav;
    }

    // 사업자 회원가입
    @Override
    @PostMapping("/addBusinessLogin.do")
    public ModelAndView addBusinessLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8"); // 요청의 인코딩 설정
        loginService.addBusinessLogin(loginDTO); // 사업자 회원가입 서비스 호출
        return new ModelAndView("redirect:/main.do"); // 회원가입 완료 후 메인 페이지로 리다이렉트
    }

    // businessForm.html에서 정보 전달용
    @Override
    @PostMapping("/businessFormProcess.do")
    public ModelAndView businessLoginForm(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                          @RequestParam(value = "action", required = false) String action,
                                          @RequestParam(value = "result", required = false) String result,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); // 세션을 가져옴
        session.setAttribute("action", action); // 세션에 action 속성 설정
        ModelAndView mav = new ModelAndView(); // ModelAndView 객체 생성
        mav.addObject("result", result); // 모델에 result 속성 추가
        mav.setViewName("/login/businessForm"); // 뷰 이름 설정
        return mav; // ModelAndView 반환
    }

    // businessForm.html 찐 로그인 용
    @Override
    @PostMapping("/businessLog.do")
    public ModelAndView businessLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                      RedirectAttributes rAttr,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        loginDTO = loginService.memberLogin(loginDTO); // 로그인 서비스 호출
        ModelAndView mav = new ModelAndView(); // ModelAndView 객체 생성
        if (loginDTO != null) { // 회원 정보가 있는 경우
            HttpSession session = request.getSession(); // 세션을 가져옴
            session.setAttribute("loginDTO", loginDTO); // 세션에 로그인 정보 저장
            session.setAttribute("isLogOn", true); // 로그인 상태 설정
            String action = (String) session.getAttribute("action"); // 세션에서 action 속성 가져옴
            if (action != null) {
                mav.setViewName("redirect:" + action); // 로그인 진행한 위치로 리다이렉트
            } else {
                mav.setViewName("redirect:/main.do"); // 기본적으로 메인 페이지로 리다이렉트
            }
        } else { // 회원 정보가 없는 경우
            rAttr.addFlashAttribute("result", "아이디나 비밀번호를 다시 입력해주세요"); // 에러 메시지 추가
            mav.setViewName("redirect:/login/businessForm.do"); // 로그인 페이지로 리다이렉트
        }
        return mav; // ModelAndView 반환
    }

    // 사람 이미지 클릭시 join.form 이동
    @Override
    @GetMapping("/join.do")
    public ModelAndView showJoin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/login/join"); // 회원가입 폼 페이지로 이동
    }

    // join에서 choiceForm으로 이동
    @Override
    @GetMapping("/choiceForm.do")
    public ModelAndView showChoiceForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/login/choiceForm"); // 선택 폼 페이지로 이동
    }

    // choiceForm에서 businessForm으로 이동
    @Override
    @GetMapping("/businessForm.do")
    public ModelAndView showBusinessForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/login/businessForm"); // 사업자 회원가입 폼 페이지로 이동
    }

    // choiceForm에서 memberForm으로 이동
    @Override
    @GetMapping("/memberForm.do")
    public ModelAndView showMemberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("/login/memberForm"); // 일반 회원가입 폼 페이지로 이동
        mav.addObject("loginDTO", new LoginDTO()); // DTO 객체를 초기화하여 뷰로 전달
        return mav; // ModelAndView 반환
    }

    // 아이디 중복 확인
    @Override
    @GetMapping("/check-id")
    @ResponseBody
    public boolean checkId(@RequestParam("user_id") String user_id) {
        return loginService.checkId(user_id); // 아이디 중복 확인 서비스 호출
    }

    // 이메일 중복 확인
    @Override
    @GetMapping("/check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email) {
        return loginService.checkEmail(email); // 이메일 중복 확인 서비스 호출
    }

    // 닉네임 중복 확인
    @Override
    @GetMapping("/check-nikname")
    @ResponseBody
    public boolean checkNikname(@RequestParam("user_nikname") String user_nikname) {
        return loginService.checkNikname(user_nikname); // 닉네임 중복 확인 서비스 호출
    }

    @Override
    public ModelAndView removeNotice(int noticeNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }



    //카카오 소셜 로그인
    @RequestMapping(value = "/popupday/kakao", method = RequestMethod.GET)
    public ModelAndView oauth(
            @RequestParam(value = "code", required = false) String code,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String access_token = loginService.getKakaoAccessToken(code);
        LoginDTO loginDTO = loginService.getKakaoUserInfo(access_token);

        if (loginDTO != null) {
            loginService.kakaoLogin(loginDTO);
            HttpSession session = request.getSession();
            session.setAttribute("loginDTO", loginDTO);
            session.setAttribute("isLogOn", true);
            session.setAttribute("sns", 1);
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/main.do");
        return mav;
    }


    @Override
    @GetMapping("/logout.do")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.invalidate(); // 세션 무효화
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/main.do"); // 로그아웃 후 메인 페이지로 리다이렉트
        return mav;
    }
}
