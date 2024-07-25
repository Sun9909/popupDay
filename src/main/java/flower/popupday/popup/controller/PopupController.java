package flower.popupday.popup.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public interface PopupController {

    public ModelAndView addPopup(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    public ModelAndView mainView(HttpServletRequest request, HttpServletResponse response) throws  Exception;

    public ModelAndView popupAllList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception;

    //admin이 보는 신청된 팝업 리스트
    public ModelAndView registerList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception;

    //신청된 팝업 상세보기
    public ModelAndView register(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    //팝업 상태 업데이트(승인, 미승인, 취소)
    public ModelAndView roleUpdate(@RequestParam("popup_id") Long popup_id, @RequestParam("role") String role, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView popupView(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView popupForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public Map<String, Object> popupLike(@RequestParam("popup_id") Long popup_id ,HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView modPopupForm(@RequestParam("popup_id") Long popup_id, @RequestParam("id") Long id,HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView updatePopup(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    //사업자가 보는 팝업 신청 상태 리스트
    public ModelAndView popupState(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception;

    //사업자의 본인이 등록한 승인된 팝업 리스트
}