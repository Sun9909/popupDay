package flower.popupday.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface AdminController {
    public ModelAndView memberShip(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView delMember(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
