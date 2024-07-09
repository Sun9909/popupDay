package flower.popupday.notice.faq.controller;
import flower.popupday.notice.faq.dto.FaqDTO;
import flower.popupday.notice.faq.service.FaqService;
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
   private FaqService faqService;

    @Autowired
    private FaqDTO faqDTO;


    
    //Faq 작성한 글 저장하기
//    @Override
//    @PostMapping("/notice/addFaq.do")
//    public ModelAndView addFaq(@ModelAttribute("faqDTO") FaqDTO faqDTO, HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//        request.setCharacterEncoding("utf-8");
//        //faqService.addFaq(faqDTO);
//        ModelAndView mav=new ModelAndView("redirect:/notice/notice.do");
//        //ModelAndView mav=new ModelAndView("notice/notice");
//        return mav; // 포워딩
//    }

//    String title=(String)articleMap.get("title");
//    String content=(String)articleMap.get("content");
//		articleDTO.setTitle(title); // 세팅
//		articleDTO.setContent(content);

    @RequestMapping("/notice/addFaq.do")
    public ModelAndView addFaq(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> articleMap = new HashMap<String, Object>();
        String title = (String)articleMap.get("title");
        String content = (String)articleMap.get("content");
        faqDTO.setTitle(title);
        faqDTO.setContent(content);
        int articleNo=faqService.addFaq(faqDTO); // 업데이트 수행(글번호로 해당 폴더 생성)
        ModelAndView mav=new ModelAndView("notice/notice");
        return mav;
    }

}
