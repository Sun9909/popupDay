package flower.popupday.mypage.controller;

import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.service.MyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("myController")
public class MyControllerImpl implements MyController {

    @Autowired
    private MyService myService;

    @Autowired
    private MyDTO myDTO;


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

        String recommentCount = myService.getreCommentCount(myDTO.getUser_nikname());
        String popcommentCount = myService.getpopCommentCount(myDTO.getUser_nikname());
        // 일단 user_id가 안되는 이유 = 값을 가져갈때 user_tbl의 user_id를 가져감 , 그래서 조회가 안됨, review_tbl의
        // user_id(FK) 값을 조회해서 select 해서 값을 들고 가면됨
        ModelAndView mav = new ModelAndView("mypage/memberPage");
        mav.addObject("my", myDTO);
        mav.addObject("reviewCount", reviewCount);
        mav.addObject("recommentCount", recommentCount);
        mav.addObject("popcommentCount", popcommentCount);
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
}
