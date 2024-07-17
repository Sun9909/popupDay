package flower.popupday.notice.qna.controller;

import flower.popupday.notice.qna.dto.QnaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface QnaController {
    ModelAndView qnaForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
    ModelAndView qnaList(HttpServletRequest request, HttpServletResponse response) throws Exception;

    // Qna 목록 가져오기
    @RequestMapping("/notice/qnaList.do")
    ModelAndView qnaList(
            @RequestParam(value = "section", required = false) String _section,
            @RequestParam(value = "pageNum", required = false) String _pageNum,
            HttpServletRequest request, HttpServletResponse response) throws Exception;

    ModelAndView addQna(HttpServletRequest request, HttpServletResponse response) throws Exception;
    ModelAndView viewQna(HttpServletRequest request, HttpServletResponse response) throws Exception;
    ModelAndView modQna(HttpServletRequest request, HttpServletResponse response) throws Exception;
    ModelAndView removeQna(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
