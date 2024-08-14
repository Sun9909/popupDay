package flower.popupday.popup_review.controller;

import flower.popupday.popup_review.dto.PopupReviewDTO;
import flower.popupday.popup_review.service.PopupReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PopupReviewControllerImpl implements PopupReviewController {

    @Autowired
    private PopupReviewService popupReviewService;

    // 리뷰 작성
    @Override
    @PostMapping("/popupComment/add")
    public String addReview(@RequestParam(value = "popup_id", required = true) Long popup_id,
                            @RequestParam(value = "user_id", required = true) Long user_id,
                            @RequestParam("content") String content,
                            @RequestParam("rating") int rating,
                            RedirectAttributes redirectAttributes) {
        // 로그 출력
        System.out.println("Received popup_id: " + popup_id);
        System.out.println("Received user_id: " + user_id);
        System.out.println("Received content: " + content);
        System.out.println("Received rating: " + rating);

        try {
            // 파라미터 확인용 로그
            if (popup_id == null || user_id == null || content == null || rating < 1 || rating > 5) {
                System.out.println("One of the parameters is null!");
                redirectAttributes.addFlashAttribute("errorMessage", "잘못된 요청입니다.");
                return "redirect:/popup/popupView";
            }

            // DTO에 데이터 전달
            PopupReviewDTO review = new PopupReviewDTO();
            review.setPopup_id(popup_id);
            review.setUser_id(user_id);
            review.setContent(content);
            review.setRating(rating);  // 별점 설정

            // 서비스 계층으로 리뷰 추가 요청
            popupReviewService.addReview(review);

            // 성공 메시지 설정
            redirectAttributes.addFlashAttribute("message", "후기가 성공적으로 저장되었습니다.");
            System.out.println("Review successfully saved.");

        } catch (Exception e) {
            e.printStackTrace(); // 예외 메시지 확인
            System.out.println("Exception occurred: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "후기 저장 중 오류가 발생했습니다.");
        }

        // 리다이렉트할 페이지로 다시 돌아가기
        return "redirect:/popup/popupView.do?popup_id=" + popup_id;
    }

    // 특정 팝업의 리뷰 조회 메서드
//    @Override
//    @GetMapping("/popupReview/view")
//    public String viewPopupReviews(@RequestParam("popup_id") long popupId, Model model) {
//        try {
//            // 특정 팝업에 대한 리뷰를 서비스 계층에서 조회
//            List<PopupReviewDTO> reviews = popupReviewService.selectReviewsByPopupId(popupId);
//            // 모델에 조회한 리뷰 목록을 추가
//            model.addAttribute("reviews", reviews);
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "리뷰 조회 중 오류가 발생했습니다.");
//        }
//        return "popup/popupView.do"; // 리뷰를 보여줄 템플릿 경로 (예: reviews.html)
//    }
    //리뷰수정
    @GetMapping("/popupComment/edit")
    public String editReviewForm(@RequestParam("id") Long reviewId, Model model) {
        // 기존의 리뷰 데이터를 가져오는 코드 수정
        PopupReviewDTO review = popupReviewService.getReviewById(reviewId);
        model.addAttribute("review", review);
        return "editReviewPopup"; // 수정 폼을 위한 뷰 (HTML 파일)
    }

    @PostMapping("/popupComment/update")
    public String updateReview(@RequestParam("id") Long reviewId,
                               @RequestParam("content") String content,
                               @RequestParam("rating") int rating) {
        PopupReviewDTO review = popupReviewService.getReviewById(reviewId);

        // 올바른 메서드 이름 사용
        popupReviewService.updateReviewContent(review.getUser_id(), content, rating);

        return "redirect:/popup/popupView"; // 수정 후 리다이렉트
    }

}
