package flower.popupday.popup_comment.controller;

import flower.popupday.popup_comment.dto.PopupCommentDTO;
import flower.popupday.popup_comment.service.PopupCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PopupCommentControllerImpl implements PopupCommentController {

    @Autowired
    private PopupCommentService popupCommentService;

    // 리뷰 작성
    @Override
    @PostMapping("/popupComment/add")
    public String addComment(@RequestParam(value = "popup_id", required = true) Long popupId,
                            @RequestParam(value = "user_id", required = true) Long userId,
                            @RequestParam("content") String content,
                            @RequestParam("rating") int rating,
                            RedirectAttributes redirectAttributes) {
        // 로그 출력
        System.out.println("Received popup_id: " + popupId);
        System.out.println("Received user_id: " + userId);
        System.out.println("Received content: " + content);
        System.out.println("Received rating: " + rating);

        try {
            // 파라미터 확인용 로그
            if (popupId == null || userId == null || content == null || rating < 1 || rating > 5) {
                System.out.println("One of the parameters is null!");
                redirectAttributes.addFlashAttribute("errorMessage", "잘못된 요청입니다.");
                return "redirect:/popup/popupView";
            }

            // DTO에 데이터 전달
            PopupCommentDTO comment = new PopupCommentDTO();
            comment.setPopup_id(popupId);
            comment.setUser_id(userId);
            comment.setContent(content);
            comment.setRating(rating);  // 별점 설정

            // 서비스 계층으로 리뷰 추가 요청
            popupCommentService.addComment(comment);

            // 성공 메시지 설정
            redirectAttributes.addFlashAttribute("message", "후기가 성공적으로 저장되었습니다.");
            System.out.println("Comment successfully saved.");

        } catch (Exception e) {
            e.printStackTrace(); // 예외 메시지 확인
            System.out.println("Exception occurred: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "후기 저장 중 오류가 발생했습니다.");
        }

        // 리다이렉트할 페이지로 다시 돌아가기
        return "redirect:/popup/popupView.do?popup_id=" + popupId;
    }

    // 특정 팝업의 리뷰 조회 메서드
//    @Override
//    @GetMapping("/popupComment/view")
//    public String viewPopupComments(@RequestParam("popup_id") long popupId, Model model) {
//        try {
//            // 특정 팝업에 대한 리뷰를 서비스 계층에서 조회
//            List<PopupCommentDTO> comments = popupCommentService.selectCommentsByPopupId(popupId);
//            // 모델에 조회한 리뷰 목록을 추가
//            model.addAttribute("comments", comments);
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "리뷰 조회 중 오류가 발생했습니다.");
//        }
//        return "popup/popupView.do"; // 리뷰를 보여줄 템플릿 경로 (예: comments.html)
//    }


    @PostMapping("/popupComment/delete")
    public String deleteComment(@RequestParam(value = "popup_id", required = true) Long popupId,
                               @RequestParam("popup_comment_id") Long popupCommentId,
                               RedirectAttributes redirectAttributes) {
        try {
            popupCommentService.deleteComment(popupCommentId);
            redirectAttributes.addFlashAttribute("message", "리뷰가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "리뷰 삭제에 실패했습니다.");
        }
        return "redirect:/popup/popupView.do?popup_id=" + popupId; // 삭제 후 리다이렉트할 페이지로 변경
    }

    //리뷰 수정
    @PostMapping("/popupComment/update")
    public String updateComment(
            @RequestParam("popup_comment_id") Long popupCommentId,
            @RequestParam("popup_id") Long popupId,
            @RequestParam("content") String content,
            @RequestParam("rating") int rating) {

        // 리뷰와 별점 업데이트 로직 수행
        popupCommentService.updateComment(popupCommentId, popupId, content, rating);

        return "redirect:/popup/popupView.do?popup_id=" + popupId; // 수정 후 리디렉트할 페이지
    }

}
