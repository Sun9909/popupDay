package flower.popupday.notice.faq.controller;
import flower.popupday.notice.faq.dto.FaqDTO;
import flower.popupday.notice.faq.service.FaqService;
import flower.popupday.notice.faq.service.FaqServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
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
    public ModelAndView faqList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int section=Integer.parseInt((_section == null) ? "1" : _section);
        int pageNum=Integer.parseInt((_pageNum == null) ? "1" : _pageNum);

        // 페이징된 FAQ 리스트 가져오기
        List<FaqDTO> faqList = faqService.listFaq(section,pageNum);

        // ModelAndView 객체 생성 및 설정
        ModelAndView mav = new ModelAndView();
        mav.setViewName("notice/faq");
        mav.addObject("faqList", faqList);
        mav.addObject("section", section);
        mav.addObject("pageNum", pageNum);
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
