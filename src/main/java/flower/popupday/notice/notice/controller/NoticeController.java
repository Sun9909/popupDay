package flower.popupday.notice.notice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface NoticeController {

    // 글목록, 패이징 처리
    public ModelAndView noticeList(@RequestParam(value = "section", required = false) String _section,
                                   @RequestParam(value = "pageNum", required = false) String _pageNum,
                                   HttpServletRequest request, HttpServletResponse response) throws DataAccessException;

    // 글 쓰기 폼
    public ModelAndView noticeForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 글 + 이미지 여러개 저장
    public ModelAndView addNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    // 여러개의 글과 이미지 수정
    public ModelAndView modNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    // 글, 이미지 삭제	    // 이미지까지 같이 삭제해야함
    ModelAndView removeNotice(@RequestParam("notice_id") Long notice_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
