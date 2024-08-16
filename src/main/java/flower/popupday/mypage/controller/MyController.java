package flower.popupday.mypage.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import flower.popupday.popup.dto.PopupDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public interface MyController {
    //마이페이지
    // 닉네임 가져오기
    public ModelAndView getName(HttpServletRequest request, HttpServletResponse response) throws Exception;
    // 회원이 작성한 댓글, 후기, 문의 개수 보기
    public ModelAndView getCount(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //최근 본 팝업 목록
    public ModelAndView recentPopup(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //내 정보 수정페이지로
    public ModelAndView loginModify(HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 내 정보 업데이트
    public ModelAndView updateLogin(HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 이메일 중복 확인
    boolean checkEmail(String email);

    // 닉네임 중복 확인
    boolean checknickname(String user_nickname);

    //비밀번호 수정페이지로
    public ModelAndView passwordModify(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView updatePwd(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView dropMember(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //public ModelAndView getBusiness(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView getBusiness(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //회원 찜목록 페이지로
    public ModelAndView memberLike(@RequestParam(value = "section", required = false) String _section,
                                   @RequestParam(value = "pageNum", required = false) String _pageNum,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception;

    //찜
    public ModelAndView unlikeClick(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView popupState(@RequestParam(value = "section", required = false) String _section,
                            @RequestParam(value = "pageNum", required = false) String _pageNum,
                            HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView popupList(
            @RequestParam(value = "section", required = false) String _section,
            @RequestParam(value = "pageNum", required = false) String _pageNum,
            HttpServletRequest request, HttpServletResponse response) throws Exception;

    //승인된 팝업 개수 사업자 페이지에 보이게
    public ModelAndView businessPage(HttpSession session);

    //내가 쓴 댓글 보기
    public ModelAndView myComment(@RequestParam(value = "section", required = false) String _section,
                                  @RequestParam(value = "pageNum", required = false) String _pageNum,
                                  @RequestParam(value = "filter", required = false, defaultValue = "popup-comment") String filter,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 내가 쓴 문의 보기
    public ModelAndView myQna(@RequestParam(value = "section", required = false) String _section,
                                  @RequestParam(value = "pageNum", required = false) String _pageNum,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 내가 쓴 리뷰 보기
    public ModelAndView myReview(@RequestParam(value = "section", required = false) String _section,
                                     @RequestParam(value = "pageNum", required = false) String _pageNum,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception;
}