package flower.popupday.popup.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface PopupController {

    public ModelAndView addPopup(MultipartHttpServletRequest multipartRequest,
                                   HttpServletResponse response) throws Exception;

    public ModelAndView popupAllList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView popupView(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response)
            throws Exception;

    public ModelAndView popupForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
