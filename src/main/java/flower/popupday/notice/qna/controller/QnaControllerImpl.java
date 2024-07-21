package flower.popupday.notice.qna.controller;

import flower.popupday.notice.qna.dto.QnaDTO;
import flower.popupday.notice.qna.service.QnaServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller("qnaController")
public class QnaControllerImpl implements QnaController {

    @Autowired
    private QnaDTO qnaDTO;

    @Autowired
    private QnaServiceImpl qnaService;



    //QNA작성폼으로 이동
    @Override
    @RequestMapping("/notice/qnaForm.do")
    public ModelAndView qnaForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav= new ModelAndView();
        mav.setViewName("/notice/qnaForm");
        return mav;
    }

    // QNA리스트로 이동
    @Override
    @RequestMapping("/notice/qnaList.do") // "/notice/qnaList.do" 요청이 들어오면 이 메서드가 처리
    public ModelAndView qnaList(@RequestParam(value = "secton", required = false) String _seciotn,
                                @RequestParam(value = "pageNum", required = false) String _pageNum,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        // secion,pageNum 파라미터가 없으면 기본값으로 1을 사용하여 정수로 변환
        int section =  Integer.parseInt((_seciotn == null) ? "1" : _seciotn);
        int pageNum =  Integer.parseInt((_pageNum == null) ? "1" : _pageNum);

        // 페이징된 QNA 리스트가져오기(section과 pageNum을 이용하여 페이징된 QNA 리스트를 서비스 계층에서 가져옴)
        List<QnaDTO> qnaList = qnaService.listQna(section,pageNum);

        // ModelAndView 객체 생성 및 설정
        ModelAndView mav =new ModelAndView();
        mav.setViewName("/notice/qna"); // 뷰 이름 설정
        mav.addObject("qnaList", qnaList); // qnaList 객체를 모델에 추가
        mav.addObject("section", section);
        mav.addObject("pageNum", pageNum);
        return mav;
    }

    @Override
    public ModelAndView addQna(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        qnaDTO.setTitle(title);
        qnaDTO.setContent(content);
        qnaService.addQna(qnaDTO);
        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
    }
    //FAQ 수정반영하기
    @Override
    @RequestMapping("/notice/modQna.do")
    public ModelAndView modQna(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String qna_id = request.getParameter("qna_id");
        qnaDTO.setTitle(title);
        qnaDTO.setContent(content);
        qnaDTO.setQna_Id(Long.parseLong(qna_id));
        qnaService.modQna(qnaDTO);
        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
    }

    //FAQ  삭제하기
    @Override
    @RequestMapping("/notice/removeQna.do")
    public ModelAndView removeQna(@RequestParam("qna_id") int qna_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        qnaService.removeQna(qna_id);
        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
    }
}
