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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    //마이페이지
    @Override
    @RequestMapping("/mypage/memberPage.do")
    public ModelAndView getName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();

        //세션에서 loginDTO 가져오기
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");

        //세션 설정
        session.setAttribute("my", loginDTO);
        session.setAttribute("isLogOn", true);

        if (loginDTO.getRole() == LoginDTO.Role.일반) {
            mav.setViewName("redirect:/mypage/reviewCount.do");
        }
        else if (loginDTO.getRole() == LoginDTO.Role.사업자){
            mav.setViewName("redirect:/mypage/businessPage.do");
        }
        else {
            mav.setViewName("/login/loginForm"); // 비 로그인시 로그인폼으로 유도
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

    //내 정보 수정으로 이동
    @Override
    @RequestMapping("/modify/loginModify.do")
    public ModelAndView loginModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");
//        //세션에서 loginDTO 가져오기
//        MyDTO myDTO = (LoginDTO) session.getAttribute("loginDTO");

        //Long reviewCount = myService.getReviewCount(loginDTO.getId());
        MyDTO myDTO=myService.findMember(loginDTO.getId());
        System.out.println(myDTO);
        ModelAndView mav = new ModelAndView("/modify/loginModify");
        mav.addObject("myInfo", myDTO);
        return mav;
    }

//    @Override
//    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        HttpSession session=request.getSession();
//        session.removeAttribute();
//    }


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