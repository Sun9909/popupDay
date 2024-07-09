package flower.popupday.popup.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface PopupController {

    public ModelAndView addArticle(MultipartHttpServletRequest multipartRequest,
                                   HttpServletResponse response) throws Exception;

}
