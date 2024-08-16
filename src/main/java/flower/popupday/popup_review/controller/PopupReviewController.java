package flower.popupday.popup_review.controller;

import flower.popupday.popup_review.dto.PopupReviewDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.*;
import java.util.Map;

public interface PopupReviewController {

    //리뷰 작성
    public String addReview(@RequestParam("popup_id") Long popupId,
                            @RequestParam("user_id") Long userId,
                            @RequestParam("content") String content,
                            @RequestParam("rating") int rating,
                            RedirectAttributes redirectAttributes);

    //특정 팝업의 리뷰 조회
    // String viewPopupReviews(@RequestParam("popupId") long popupId, Model model);

    //리뷰 수정
    String editReviewForm(
            @RequestParam("user_id") Long userId,
            @RequestParam("comment_id") Long commentId,
            Model model);

    //리뷰 삭제
    String deleteReview(@RequestParam(value = "popup_id", required = true) Long popup_id,
                        @RequestParam("popup_comment_id") Long popupCommentId,
                        RedirectAttributes redirectAttributes);
}

