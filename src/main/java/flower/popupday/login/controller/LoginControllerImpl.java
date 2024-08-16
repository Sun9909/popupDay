package flower.popupday.login.controller;

import flower.popupday.login.dao.LoginDAO;
import flower.popupday.login.dto.LoginDTO;
import flower.popupday.login.dto.LoginHashTagDTO;
import flower.popupday.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginControllerImpl implements LoginController {

    @Autowired
    private LoginService loginService; // LoginService 객체를 자동 주입
    @Autowired
    private LoginDAO loginDAO;

//    // 일반 회원가입
//    @Override
//    @PostMapping("/addLogin.do")
//    public ModelAndView addLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
//                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
//        request.setCharacterEncoding("utf-8"); // 요청 인코딩 설정
//        loginService.addLogin(loginDTO); // 회원 가입 서비스 호출
//
//        HttpSession session = request.getSession();
//        String action = (String) session.getAttribute("action"); // 세션에서 action 값 가져오기
//
//        ModelAndView mav = new ModelAndView();
//        if (action != null) {
//            mav.setViewName("redirect:" + action); // action 값이 있으면 해당 경로로 리디렉션
//        } else {
//            mav.setViewName("redirect:/main.do"); // action 값이 없으면 메인 페이지로 리디렉션
//        }
//        return mav; // ModelAndView 반환
//    }



    //일반회원가입 + 해시태그
    @Override
    @PostMapping("/addLogin")
    public ModelAndView addLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        try {
            // 서비스 호출 및 로그 확인
            System.out.println("Received loginDTO: " + loginDTO);
            System.out.println("Received has_tag_id: " + loginDTO.getHash_tag_id());

            // LoginDTO에서 해시태그 ID 리스트를 가져와서 서비스 메서드에 전달
            loginService.addLogin(loginDTO, loginDTO.getHash_tag_id());

            // 성공적으로 처리되었을 경우의 뷰 반환 (예: 로그인 페이지로 리다이렉트)
            return new ModelAndView("redirect:/login");

        } catch (Exception e) {
            e.printStackTrace(); // 예외를 로그로 기록

            // 오류 메시지를 모델에 추가하여 뷰에서 접근할 수 있도록 함
            ModelAndView mav = new ModelAndView("errorView"); // 에러 뷰로 리다이렉트
            mav.addObject("errorMessage", "회원가입 중 오류가 발생했습니다.");
            return mav;
        }
    }





    // 로그인 폼 이동 및 로그인 실패 시 오류 메시지를 처리
    @Override
    @GetMapping("/login.do")
    public String loginForm(HttpServletRequest request, Model model) {
        // 요청에서 세션을 가져옵니다. 세션이 없으면 null을 반환합니다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 세션에서 오류 메시지를 가져옵니다.
            String errorMessage = (String) session.getAttribute("errorMessage");
            if (errorMessage != null) {
                // 모델에 오류 메시지를 추가합니다.
                model.addAttribute("errorMessage", errorMessage);
                // 세션에서 오류 메시지를 제거합니다.
                session.removeAttribute("errorMessage");
            }
        }
        // 로그인 페이지로 이동합니다.
        return "login/login";
    }


    // 로그인 처리 메서드
    @Override
    @PostMapping("/log.do")
    public ModelAndView memberLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                    RedirectAttributes rAttr,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        LoginDTO loginResult = loginService.memberLogin(loginDTO);
        ModelAndView mav = new ModelAndView();

        if (loginResult != null) {
            LoginDTO.Status status = loginResult.getStatus();
            if (status == LoginDTO.Status.deleted) {
                rAttr.addFlashAttribute("flashMessage", "탈퇴한 회원입니다");
                rAttr.addFlashAttribute("flashType", "error");
                mav.setViewName("redirect:/login/login.do");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("loginDTO", loginResult);
                session.setAttribute("isLogOn", true);
                String action = (String) session.getAttribute("action");
                if (action != null) {
                    mav.setViewName("redirect:" + action);
                } else {
                    mav.setViewName("redirect:/main.do");
                }
            }
        } else {
            rAttr.addFlashAttribute("flashMessage", "아이디 또는 비밀번호를 다시 입력해주세요");
            rAttr.addFlashAttribute("flashType", "error");
            mav.setViewName("redirect:login/login.do");
        }
        return mav;
    }

    //로그인 실패 시 오류 메시지를 처리하는 메서드
    @GetMapping("/login/login.do")
    public String showLoginPage(HttpServletRequest request, Model model) {
        // 요청에서 세션을 가져옵니다. 세션이 없으면 null을 반환합니다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 세션에서 오류 메시지를 가져옵니다.
            String errorMessage = (String) session.getAttribute("errorMessage");
            if (errorMessage != null) {
                // 모델에 오류 메시지를 추가합니다.
                model.addAttribute("errorMessage", errorMessage);
                // 세션에서 오류 메시지를 제거합니다.
                session.removeAttribute("errorMessage");
            }
        }
        // 로그인 페이지로 이동합니다.
        return "login/login";
    }

    // 사업자 회원가입
    @Override
    @PostMapping("/addBusinessLogin.do")
    public ModelAndView addBusinessLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8"); // 요청의 인코딩 설정
        loginService.addBusinessLogin(loginDTO); // 사업자 회원가입 서비스 호출

        HttpSession session = request.getSession();
        String action = (String) session.getAttribute("action"); // 세션에서 action 값 가져오기

        ModelAndView mav = new ModelAndView();
        if (action != null) {
            mav.setViewName("redirect:" + action); // action 값이 있으면 해당 경로로 리디렉션
        } else {
            mav.setViewName("redirect:/main.do"); // action 값이 없으면 메인 페이지로 리디렉션
        }
        return mav; // ModelAndView 반환
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
        mav.setViewName("login/businessForm"); // 뷰 이름 설정
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
                mav.setViewName("redirect:/main.do"); // 기본적으로 메인 페이지로 리디렉트
            }
        } else { // 회원 정보가 없는 경우
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "아이디나 비밀번호를 다시 입력해주세요");
            mav.setViewName("redirect:login/login.do");
        }
        return mav; // ModelAndView 반환
    }

    // join에서 choiceForm으로 이동
    @Override
    @GetMapping("/choiceForm.do")
    public ModelAndView showChoiceForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("login/joinChoice"); // 선택 폼 페이지로 이동
    }

    // choiceForm에서 businessForm으로 이동
    @Override
    @GetMapping("/businessForm.do")
    public ModelAndView showBusinessForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("login/businessForm"); // 사업자 회원가입 폼 페이지로 이동
    }

    // choiceForm에서 memberForm으로 이동
    @Override
    @GetMapping("/memberForm.do")
    public ModelAndView showMemberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("login/memberForm"); // 일반 회원가입 폼 페이지로 이동

        List<LoginHashTagDTO> hashtagList = loginService.hashtagList();  // 해시태그 조회
        mav.addObject("loginDTO", new LoginDTO()); // DTO 객체를 초기화하여 뷰로 전달
        mav.addObject("hashtagList", hashtagList);  // 해시태그 조회
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
    @GetMapping("/check-nickname")
    @ResponseBody
    public boolean checknickname(@RequestParam("user_nickname") String user_nickname) {
        return loginService.checknickname(user_nickname); // 닉네임 중복 확인 서비스 호출
    }

    //회원 탈퇴 확인
    @Override
    @GetMapping("/drop-id")
    @ResponseBody
    public boolean dropCheck(String user_id) {
        return loginService.dropCheck(user_id);
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

        String access_token = loginService.getKakaoAccessToken(code); // 카카오 인증 코드로 액세스 토큰 받기
        LoginDTO loginDTO = loginService.getKakaoUserInfo(access_token); // 액세스 토큰으로 사용자 정보 가져오기

        if (loginDTO != null) {
            loginService.kakaoLogin(loginDTO); // 카카오 사용자 로그인 처리
            HttpSession session = request.getSession();
            session.setAttribute("loginDTO", loginDTO); // 로그인 정보 세션에 저장
            session.setAttribute("isLogOn", true); // 로그인 상태 설정
            session.setAttribute("sns", 1); // 소셜 로그인 플래그 설정

            String action = (String) session.getAttribute("action"); // 세션에서 action 값 가져오기
            if (action != null) {
                return new ModelAndView("redirect:" + action); // action 값이 있으면 해당 경로로 리디렉션
            } else {
                return new ModelAndView("redirect:/main.do"); // action 값이 없으면 메인 페이지로 리디렉션
            }
        }

        return new ModelAndView("redirect:/login/login.do"); // 로그인 실패 시 로그인 페이지로 리디렉션
    }

    @Override
    @GetMapping("/logout.do")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.invalidate(); // 세션 무효화
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/main.do"); // 로그아웃 후 메인 페이지로 리디렉트
        return mav;
    }

    @PostMapping("/clear-error-message")
    public void clearErrorMessage(HttpSession session) {
        session.removeAttribute("errorMessage");
    }

}
