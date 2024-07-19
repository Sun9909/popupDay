package flower.popupday.mypage.controller;


import flower.popupday.login.dto.LoginDTO;
import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import flower.popupday.mypage.service.MyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller("myController")
public class MyControllerImpl implements MyController {

    @Autowired
    private MyService myService;

//    @Autowired
//    private MyDTO myDTO;
    @Autowired
    private MyPopupDTO myPopupDTO;
    @Autowired
    private LoginDTO loginDTO;
    @Autowired
    private MyDTO myDTO;

    //마이페이지
    @Override
    @RequestMapping("/mypage/memberPage.do")
    public ModelAndView getName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();

        //세션에서 loginDTO 가져오기
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");

        // 세션에 myDTO 설정
        session.setAttribute("my", loginDTO);
        session.setAttribute("isLogOn", true);

        // 로그인된 사용자의 역할(role)에 따라 리다이렉트 설정
        if (loginDTO.getRole() == LoginDTO.Role.일반) {
            mav.setViewName("redirect:/mypage/reviewCount.do"); // 리뷰 카운트 페이지로 리다이렉트
        }
        else if(loginDTO.getRole() == LoginDTO.Role.사업자) {
            mav.setViewName("/mypage/businessPage");
        }
        else {
            mav.setViewName("redirect:/login/loginForm"); // 로그인 폼으로 유도
        }
        return mav;
    }

    @Override
    @RequestMapping("/mypage/reviewCount.do")
    public ModelAndView getCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");
        //myDTO=myService.getName(myDTO);
        Long reviewCount = myService.getReviewCount(loginDTO.getId()); // 리뷰 개수 조회

        String recommentCount = myService.getreCommentCount(loginDTO.getUser_nikname()); //리뷰댓글
        String popcommentCount = myService.getpopCommentCount(loginDTO.getUser_nikname()); //팝업댓글

        Long qnaCount = myService.getQnaCount(loginDTO.getId());
        // 일단 user_id가 안되는 이유 = 값을 가져갈때 user_tbl의 user_id를 가져감 , 그래서 조회가 안됨, review_tbl의
        // user_id(FK) 값을 조회해서 select 해서 값을 들고 가면됨
        ModelAndView mav = new ModelAndView("/mypage/memberPage");
        mav.addObject("my", loginDTO);
        mav.addObject("reviewCount", reviewCount);
        mav.addObject("recommentCount", recommentCount);
        mav.addObject("popcommentCount", popcommentCount);
        mav.addObject("qnaCount", qnaCount);
        return mav;
    }

    //내 정보 수정 페이지로 이동
    @Override
    @RequestMapping("/modify/loginModify.do")
    public ModelAndView loginModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); //세션 가져오기(사용자 상태 유지를 위해)
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");    //loginDTO 속성으로 저장된 객체를 가져와 LoginDTO 타입으로 캐스팅. 사용자의 로그인 정보를 담고 있음

        MyDTO myDTO=myService.findMember(loginDTO.getId()); //사용자 id를 가져와 서비스의 findMember 메소드를 호출하여 MyDTO객체를 반환받음. 사용자의 상세 정보를 담고 있음
        ModelAndView mav = new ModelAndView("/modify/loginModify"); // 새로운 ModelAndView 객체 생성. 포워딩?
        mav.addObject("myInfo", myDTO); //myDTO객체를 myInfo라는 이름으로 ModelAndView 객체에 추가. 이 데이터가 뷰에서 사용됨. 바인딩?
        return mav;
    }

    //자신의 정보를 수정한 후 저장하기
    @Override
    @RequestMapping("/mypage/updateLogin.do")
    public ModelAndView updateLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); //세션 가져오기
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");    //세션에서 loginDTO 가져오기

        //loginDTO.setId(sessionLoginDTO.getId());  //세션에서 가져온 id를 설정하여 보안 유지
        //MyDTO myDTO = new MyDTO();
        loginDTO.setUser_nikname(request.getParameter("user_nikname"));
        loginDTO.setName(request.getParameter("name"));
        loginDTO.setEmail(request.getParameter("email"));

        myService.updateLogin(loginDTO);   //loginDTO를 서비스로 전달
        session.setAttribute("loginDTO", loginDTO); //업데이트된 정보를 세션에 저장
        //System.out.println(loginDTO);
        //System.out.println(myDTO);

        ModelAndView mav = new ModelAndView("redirect:/mypage/memberPage.do");
        return mav;
    }

    //이메일 중복 확인
    @Override
    @RequestMapping("/mypage/check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email) {
        return myService.checkEmail(email);
    }

    //닉네임 중복 확인
    @Override
    @RequestMapping("/mypage/check-nikname")
    @ResponseBody
    public boolean checkNikname(@RequestParam("user_nikname") String user_nikname) {
        return myService.checkNikname(user_nikname);
    }

    @Override
    @RequestMapping("/modify/passwordModify.do")
    public ModelAndView passwordModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); //세션 가져오기(사용자 상태 유지를 위해)
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");    //loginDTO 속성으로 저장된 객체를 가져와 LoginDTO 타입으로 캐스팅. 사용자의 로그인 정보를 담고 있음

        MyDTO myDTO=myService.findMember(loginDTO.getId()); //사용자 id를 가져와 서비스의 findMember 메소드를 호출하여 MyDTO객체를 반환받음. 사용자의 상세 정보를 담고 있음
        ModelAndView mav = new ModelAndView("/modify/passwordModify"); // 새로운 ModelAndView 객체 생성. 포워딩?
        mav.addObject("myInfo", myDTO); //myDTO객체를 myInfo라는 이름으로 ModelAndView 객체에 추가. 이 데이터가 뷰에서 사용됨. 바인딩?
        return mav;
    }

    @Override
    @RequestMapping("/mypage/updatePwd.do")
    public ModelAndView updatePwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");

        loginDTO.setPwd(request.getParameter("newpwd"));

        myService.updatePwd(loginDTO);
        session.setAttribute("loginDTO", loginDTO);

        ModelAndView mav = new ModelAndView("redirect:/mypage/memberPage.do");
        return mav;
    }

    @Override
    @RequestMapping("/mypage/businessPage.do")
    public ModelAndView getBusiness(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");

        ModelAndView mav = new ModelAndView("/mypage/businessPage");
        return mav;
    }

    //다음 개수 조회 시 서비스 호출
    //Long reviewCount = myService.getReviewCount(myDTO.getId());
    //mav.addObject("reviewCount", reviewCount);
    //찜목록 상세보기에 추가하기
//    @Override
//    @RequestMapping("/mypage/reviewCount.do")
//    public ModelAndView getCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        myDTO=myService.getName(myDTO);
//        Long likeListCount = myService.getLikeListCount(myDTO.getId()); // 리뷰 개수 조회
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("likeListCount", likeListCount);
//        return mav;
//    }

    //팝업리스트
    @Override
    @RequestMapping("/mypage/myPopup.do")
    public ModelAndView getPopup(@ModelAttribute("mypopupDTO") MyPopupDTO mypopupDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //mypopupDTO=myService.getPopup(mypopupDTO);
        String user_id = request.getParameter("user_id");
        //List<mypopupDTO> myPopup = new ArrayList<mypopupDTO>(); //한개만 가져오는건 가능 map을 사용하던 list를 사용해서 여러개 가져오도록
        List<MyPopupDTO> myPopup = myService.getPopup(user_id);

        Long PopupCount = myService.getPopupCount(mypopupDTO.getUser_id());

        ModelAndView mav = new ModelAndView("/mypage/myPopup");

        mav.addObject("myPopup", myPopup);
        mav.addObject("PopupCount", PopupCount);
        return mav;
    }

//    @Override
//    public ModelAndView getPopupCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Long PopupCount = myService.getReviewCount(mypopupDTO.getHash_tag_id());
//        ModelAndView mav = new ModelAndView("/mypage/myPopup");
//        mav.addObject("PopupCount", PopupCount);
//        return mav;
//    }
}