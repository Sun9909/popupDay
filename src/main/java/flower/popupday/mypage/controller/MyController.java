package flower.popupday.mypage.controller;

import flower.popupday.mypage.dto.MyDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

public interface MyController {
    public ModelAndView getName(@ModelAttribute("myDTO") MyDTO myDTO, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView getCount(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
