package flower.popupday.notice.notice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface NoticeController {

    // 글목록 페이징처리

    //글쓰기
    public ModelAndView adminNotice(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //글저장(이미지 첨부가능)
    public ModelAndView addNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws  Exception;

    //상세글보기
    public ModelAndView adminNoticeView(@RequestParam("notice_id") Long notice_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    //글수정(이미지 수정가능)
    public ModelAndView modArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    //글삭제(글번호를 받아서)
    public ModelAndView removeArticle(@RequestParam("notice_id") Long notice_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
