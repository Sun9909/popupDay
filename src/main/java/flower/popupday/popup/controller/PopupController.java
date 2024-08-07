package flower.popupday.popup.controller;

import flower.popupday.popup.dto.PopupDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

public interface PopupController {

    public ModelAndView addPopup(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    public ModelAndView mainView(HttpServletRequest request, HttpServletResponse response) throws  Exception;

    public ResponseEntity<Map<String, Object>> selectPopupList(@RequestBody Map<String, String> params);

    ModelAndView popupAllList(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView popupView(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView popupForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public Map<String, Object> popupLike(@RequestParam("popup_id") Long popup_id ,HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView modPopupForm(@RequestParam("popup_id") Long popup_id, @RequestParam("id") Long id,HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView updatePopup(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    public ResponseEntity<Map<String, Object>> searchPopupHasTag(@RequestBody Map<String, String> request);

}