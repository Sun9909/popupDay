package flower.popupday.notice.faq.controller;
import flower.popupday.notice.faq.dto.FaqDTO;
import flower.popupday.notice.faq.service.FaqService;
import flower.popupday.notice.faq.service.FaqServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller("faqController")
public class FaqControllerImpl implements FaqController {
    @Autowired
    private FaqDTO faqDTO;
    @Autowired
    private FaqServiceImpl faqService;


    //Faq작성 폼으로 이동
    @Override
    @RequestMapping("/notice/getFaq.do")
    public ModelAndView faqForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("notice/faqForm");
        return mav;
    }

    //Faq 리스트로 이동
    @Override
    @RequestMapping("/notice/faqList.do")
    public ModelAndView faqList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("notice/notice");
        return mav;
    }

    @Override
    @RequestMapping("/notice/addFaq.do")
    public ModelAndView addFaq(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        faqDTO.setTitle(title);
        faqDTO.setContent(content);
        faqService.addFaq(faqDTO);
        ModelAndView mav = new ModelAndView("redirect:/notice/faqList.do");
        return mav;
    }


}
