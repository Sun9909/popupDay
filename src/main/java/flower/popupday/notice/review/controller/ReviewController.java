package flower.popupday.notice.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface ReviewController {

    public ModelAndView reviewList(HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 글저장 (이미지 첨부도 가능하게)
    public ModelAndView addReview(MultipartHttpServletRequest multipartRequest,
                                   HttpServletResponse response) throws Exception;
}
