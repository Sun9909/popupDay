package flower.popupday.mypage.controller;

import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import flower.popupday.mypage.service.MyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Autowired
    private MyDTO myDTO;
    @Autowired
    private MyPopupDTO mypopupDTO;

    //마이페이지
    @Override
    @RequestMapping("/mypage/memberPage.do")
    public ModelAndView getName(@ModelAttribute("myDTO") MyDTO myDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        myDTO=myService.getName(myDTO);
        ModelAndView mav = new ModelAndView();
        if (myDTO.getRole() == MyDTO.Role.일반) {
            mav.setViewName("redirect:/mypage/reviewCount.do");
        }
        else if (myDTO.getRole() == MyDTO.Role.사업자){
            mav.setViewName("redirect:/mypage/reviewCount.do");
        }
        else {
            mav.setViewName("/login/loginForm"); // 비 로그인시 로그인폼으로 유도
        }
        return mav;
    }

    @Override
    @RequestMapping("/mypage/reviewCount.do")
    public ModelAndView getCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        myDTO=myService.getName(myDTO);
        Long reviewCount = myService.getReviewCount(myDTO.getId()); // 리뷰 개수 조회

        String recommentCount = myService.getreCommentCount(myDTO.getUser_nikname()); //리뷰댓글
        String popcommentCount = myService.getpopCommentCount(myDTO.getUser_nikname()); //팝업댓글

        Long qnaCount = myService.getQnaCount(myDTO.getId());
        // 일단 user_id가 안되는 이유 = 값을 가져갈때 user_tbl의 user_id를 가져감 , 그래서 조회가 안됨, review_tbl의
        // user_id(FK) 값을 조회해서 select 해서 값을 들고 가면됨
        ModelAndView mav = new ModelAndView("mypage/memberPage");
        mav.addObject("my", myDTO);
        mav.addObject("reviewCount", reviewCount);
        mav.addObject("recommentCount", recommentCount);
        mav.addObject("popcommentCount", popcommentCount);
        mav.addObject("qnaCount", qnaCount);
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
