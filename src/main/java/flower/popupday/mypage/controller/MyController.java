package flower.popupday.mypage.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

public interface MyController {
    //마이페이지
    // 닉네임 가져오기
    public ModelAndView getName(HttpServletRequest request, HttpServletResponse response) throws Exception;
    // 회원이 작성한 댓글, 후기, 문의 개수 보기
    public ModelAndView getCount(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //내 정보 수정하기
    public ModelAndView loginModify(HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 로그아웃
//    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception;


    //팝업리스트
    public ModelAndView getPopup(@ModelAttribute("mypopupDTO") MyPopupDTO mypopupDTO, HttpServletRequest request, HttpServletResponse response) throws Exception;

    //public ModelAndView getPopupCount(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
