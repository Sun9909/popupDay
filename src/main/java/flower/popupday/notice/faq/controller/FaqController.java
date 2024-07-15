package flower.popupday.notice.faq.controller;

import flower.popupday.notice.faq.dto.FaqDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface FaqController {

    public ModelAndView addFaq(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView faqForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView faqList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum,HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView modFaq(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //FAQ  삭제하기
    ModelAndView removeFaq(@RequestParam("faq_id") int faq_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
