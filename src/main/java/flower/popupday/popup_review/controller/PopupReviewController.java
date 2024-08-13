package flower.popupday.popup_review.controller;

import flower.popupday.popup_review.dto.PopupReviewDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.*;

public interface PopupReviewController {

    //리뷰 작성
    public String addReview(@RequestParam("popup_id") Long popupId,
                            @RequestParam("user_id") Long userId,
                            @RequestParam("content") String content,
                            RedirectAttributes redirectAttributes);

    //특정 팝업의 리뷰 조회
    // String viewPopupReviews(@RequestParam("popupId") long popupId, Model model);

}
