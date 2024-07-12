package flower.popupday.notice.notice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public interface NoticeController {

    // 글목록 페이징처리

    //페이징 처리 해보자
    Map<String, Object> notice(Map<String, Integer> pagingMap) throws DataAccessException;

    //글쓰기 폼으로 이동
    @GetMapping("/admin/adminNotice.do") // 공지사항 폼으로 이동 (url이 호출될 때 해당 메서드 실행)
    //notice메서드는 ModelAndView 객체로 반환
    ModelAndView notice(HttpServletRequest request, HttpServletResponse response) throws Exception;

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
