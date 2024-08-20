package flower.popupday.business.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface StatsController {

    public ModelAndView StatsList(@RequestParam("popup_id") Long popup_id, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception;
}
