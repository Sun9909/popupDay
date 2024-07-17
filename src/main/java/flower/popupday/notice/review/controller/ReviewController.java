package flower.popupday.notice.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface ReviewController {

    public ModelAndView reviewList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 글저장 (이미지 첨부도 가능하게)
    public ModelAndView addReview(MultipartHttpServletRequest multipartRequest,
                                   HttpServletResponse response) throws Exception;

    //후기 수정적용하기
    public ModelAndView modReview(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    //후기작성페이지로 이동
    public ModelAndView reviewForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //후기 상세페이지로 이동
    public ModelAndView showReview(@RequestParam("review_id") int review_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    //후기 삭제하기
    ModelAndView removeReview(@RequestParam("review_id") int review_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
