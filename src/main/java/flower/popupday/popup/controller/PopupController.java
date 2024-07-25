package flower.popupday.popup.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public interface PopupController {

    public ModelAndView addPopup(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    public ModelAndView popupAllList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception;

    //admin
    public ModelAndView registerList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView register(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView roleUpdate(@RequestParam("popup_id") Long popup_id, @RequestParam("role") String role, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView popupView(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView popupForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public Map<String, Object> popupLike(@RequestParam("popup_id") Long popup_id ,HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView modPopupForm(@RequestParam("popup_id") Long popup_id, @RequestParam("id") Long id,HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView updatePopup(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;
}