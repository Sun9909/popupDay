package flower.popupday.notice.qna.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.notice.qna.dto.QnaDTO;
import flower.popupday.notice.qna.service.QnaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("qnaController")
public class QnaControllerImpl implements QnaController {

    @Autowired
    private QnaService qnaService;

    @Autowired
    private QnaDTO qnaDTO;

    // Qna 작성 폼으로 이동
    @RequestMapping("/notice/qnaForm.do")
    @Override
    public ModelAndView qnaForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/notice/qnaForm");
        return mav;
    }



    // Qna 목록 가져오기
    @RequestMapping("/notice/qnaList.do")
    @Override
    public ModelAndView qnaList(
            @RequestParam(value = "section", required = false) String _section,
            @RequestParam(value = "pageNum", required = false) String _pageNum,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        int section = Integer.parseInt((_section == null) ? "1" : _section);
        int pageNum = Integer.parseInt((_pageNum == null) ? "1" : _pageNum);

        Map<String, Integer> pagingMap = new HashMap<>();
        pagingMap.put("section", section);
        pagingMap.put("pageNum", pageNum);

        List<QnaDTO> qnaList = (List<QnaDTO>) qnaService.listQna(pagingMap);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/notice/qna");
        mav.addObject("qnaList", qnaList);
        mav.addObject("section", section);
        mav.addObject("pageNum", pageNum);
        return mav;
    }

    // 새 글 등록
    @RequestMapping("/notice/addQna.do")
    @Override
    public ModelAndView addQna(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        Map<String, Object> QnaMap = new HashMap<>();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            String value = request.getParameter(name);
            QnaMap.put(name, value);
        }

        /*String title = (String) QnaMap.get("title");
        String content = (String) QnaMap.get("content");

        QnaDTO qnaDTO = new QnaDTO();
        qnaDTO.setTitle(title);
        qnaDTO.setContent(content);*/

        HttpSession session = request.getSession();

        //회원인증구현
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("member");
        if (loginDTO != null) {
            String user_Id = String.valueOf(loginDTO.getId());
            qnaDTO.setUser_Id(Long.valueOf(user_Id));
        }

        qnaService.addQna(QnaMap);

        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
    }


    // 상세글 보기
    @RequestMapping("/notice/qna.do")
    @Override
    public ModelAndView viewQna(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long qnaId = Long.parseLong(request.getParameter("qnaId"));

        QnaDTO qna = qnaService.getQnaById(qnaId);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/notice/qnaView");
        mav.addObject("qna", qna);
        return mav;
    }

    // 글 수정
    @RequestMapping("/notice/modQna.do")
    @Override
    public ModelAndView modQna(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long qnaId = Long.parseLong(request.getParameter("qnaId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        QnaDTO qna = qnaService.getQnaById(qnaId);
        if (qna != null) {
            qna.setTitle(title);
            qna.setContent(content);
            qnaService.updateQna(qna);
        }

        ModelAndView mav = new ModelAndView("redirect:/notice/viewQna.do?qnaId=" + qnaId);
        return mav;
    }

    // 글 삭제
    @RequestMapping("/notice/removeQna.do")
    @Override
    public ModelAndView removeQna(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long qnaId = Long.parseLong(request.getParameter("qnaId"));

        qnaService.deleteQna(qnaId);

        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
    }
}
