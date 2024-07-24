package flower.popupday.notice.qna.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface QnaController {

    // 폼
    public ModelAndView qnaForm (HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 리스트
    public ModelAndView qnaList (@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum,HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView addQna(HttpServletRequest request)throws Exception;

    public ModelAndView qnaView(@RequestParam("qna_id") long qna_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView modQna(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //public ModelAndView modQna(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView removeQna(@RequestParam("qna_id") long qna_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 답글 쓰기 화면 요청
    public ModelAndView answer(@RequestParam("qna_id") long qna_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 답글 저장
    public ModelAndView addAnswer(HttpServletRequest request, HttpServletResponse response) throws Exception;
}