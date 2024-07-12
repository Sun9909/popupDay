package flower.popupday.mypage.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.ModelAndView;

public interface MyController {
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
