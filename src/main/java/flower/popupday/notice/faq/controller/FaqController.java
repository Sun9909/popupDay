package flower.popupday.notice.faq.controller;

import flower.popupday.notice.faq.dto.FaqDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

public interface FaqController {
//    public ModelAndView addFaq(@ModelAttribute("faqDTO") FaqDTO faqDTO,HttpServletRequest request,
//                                  HttpServletResponse response) throws Exception;
//
    public ModelAndView addFaq(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
