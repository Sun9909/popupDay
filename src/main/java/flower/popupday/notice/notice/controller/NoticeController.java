package flower.popupday.notice.notice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface NoticeController {

    // 공지사항 리스트
    public ModelAndView noticeList(@RequestParam(value = "section", required = false) String _section,
                                   @RequestParam(value = "pageNum", required = false) String _pageNum,
                                   HttpServletRequest request, HttpServletResponse response) throws DataAccessException;

    // 글저장(이미지 첨부도 가능하게)
    public ModelAndView addNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    // 공지사항 수정적용하기
    public ModelAndView modNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    // 공지사항 작성페이지로 이동
    public ModelAndView noticeForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //공지사항 상세페이지로 이동
    public ModelAndView noticeView(@RequestParam("notice_id")  long notice_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 공지사항 삭제
    ModelAndView removeNotice(@RequestParam("notice_id") long notice_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
