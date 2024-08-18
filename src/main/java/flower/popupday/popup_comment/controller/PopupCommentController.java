package flower.popupday.popup_comment.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface PopupCommentController {

    //리뷰 작성
    public String addComment(@RequestParam("popup_id") Long popupId,
                            @RequestParam("user_id") Long userId,
                            @RequestParam("content") String content,
                            @RequestParam("rating") int rating,
                            RedirectAttributes redirectAttributes);

    //특정 팝업의 리뷰 조회
    // String viewPopupComments(@RequestParam("popupId") long popupId, Model model);


    //리뷰 삭제
    String deleteComment(@RequestParam(value = "popup_id", required = true) Long popup_id,
                        @RequestParam("popup_comment_id") Long popupCommentId,
                        RedirectAttributes redirectAttributes);

    //리뷰 수정
    public String updateComment(
            @RequestParam("popup_comment_id") Long popupCommentId,
            @RequestParam("popup_id") Long popupId,
            @RequestParam("content") String content,
            @RequestParam("rating") int rating);
}

