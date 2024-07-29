package flower.popupday.notice.faq.controller;
import flower.popupday.login.dto.LoginDTO;
import flower.popupday.notice.faq.dto.FaqDTO;
import flower.popupday.notice.faq.service.FaqServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    //FAQ 글 추가하기
    @Override
    @RequestMapping("/notice/addFaq.do")
    public ModelAndView addFaq(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        HttpSession session=request.getSession();
        LoginDTO loginDTO=(LoginDTO)session.getAttribute("loginDTO");
        Long id=loginDTO.getId();
        faqDTO.setTitle(title);
        faqDTO.setContent(content);
        faqDTO.setUser_id(id);
        faqService.addFaq(faqDTO);
        ModelAndView mav = new ModelAndView("redirect:/notice/faqList.do");
        return mav;
    }

    //FAQ 수정반영하기
    @Override
    @RequestMapping("/notice/modFaq.do")
    public ModelAndView modFaq(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String faq_id = request.getParameter("faq_id");
        faqDTO.setTitle(title);
        faqDTO.setContent(content);
        faqDTO.setFaq_id(Long.parseLong(faq_id));
        faqService.modFaq(faqDTO);
        ModelAndView mav = new ModelAndView("redirect:/notice/faqList.do");
        return mav;
    }

    //FAQ  삭제하기
    @Override
    @RequestMapping("/notice/removeFaq.do")
    public ModelAndView removeFaq(@RequestParam("faq_id") int faq_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        faqService.removeFaq(faq_id);
        ModelAndView mav = new ModelAndView("redirect:/notice/faqList.do");
        return mav;
    }

}
