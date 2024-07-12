package flower.popupday.notice.notice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface NoticeController {

    // 글목록 페이징처리
    public ModelAndView noticeList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws DataAccessException;

    //글씨기
    public ModelAndView noticeForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //글저장(이미지 첨부가능)
    public ModelAndView addNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws  Exception;
    default List<String> multiFileUpload(MultipartHttpServletRequest multipartRequest) {
        return null;
    }
    //상세글보기
    public ModelAndView adminNoticeView(@RequestParam("notice_id") Long notice_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    //글수정(이미지 수정가능)
   public ModelAndView modNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    //글삭제(글번호를 받아서)
   public ModelAndView removeNotice(@RequestParam("notice_id") Long notice_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
