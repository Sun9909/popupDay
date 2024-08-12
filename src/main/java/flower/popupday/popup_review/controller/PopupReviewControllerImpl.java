package flower.popupday.popup_review.controller;

import flower.popupday.popup_review.dto.PopupReviewDTO;
import flower.popupday.popup_review.service.PopupReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PopupReviewControllerImpl implements PopupReviewController {

    @Autowired
    private PopupReviewService popupReviewService;

    //리뷰 작성
    @Override
    @PostMapping("/popupReview/add")
    public String addReview(@RequestParam(value = "popup_id", required = true) Long popupId,
                            @RequestParam(value = "user_id", required = true) Long userId,
                            @RequestParam(value = "rating", required = false, defaultValue = "0") int rating,
                            @RequestParam("content") String content,
                            RedirectAttributes redirectAttributes) {
        // 디버깅용 로그 추가
        System.out.println("Received popup_id: " + popupId);
        System.out.println("Received user_id: " + userId);
        System.out.println("Received rating: " + rating);
        System.out.println("Received content: " + content);

        try {
            // 사용자가 별점을 선택하지 않았을 경우
            if (rating == 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "별점을 선택해 주세요.");
                return "redirect:/popup/popupView?popup_id=" + popupId;
            }

            // DTO에 데이터 전달
            PopupReviewDTO review = new PopupReviewDTO();
            review.setPopup_id(popupId);
            review.setUser_id(popupId);
            review.setRating(rating);
            review.setContent(content);

            // 서비스 계층으로 리뷰 추가 요청
            popupReviewService.addReview(review);

            // 성공 메시지 설정
            redirectAttributes.addFlashAttribute("message", "후기가 성공적으로 저장되었습니다.");

        } catch (Exception e) {
            e.printStackTrace(); // 예외 메시지 확인
            redirectAttributes.addFlashAttribute("errorMessage", "후기 저장 중 오류가 발생했습니다.");
        }

        // 리다이렉트할 페이지로 다시 돌아가기
        return "redirect:/popup/popupView?popup_id=" + popupId;
    }

    // 특정 팝업의 리뷰 조회 메서드
    @Override
    @GetMapping("/popupReview/view")
    public String viewPopupReviews(@RequestParam("popupId") long popupId, Model model) {
        try {
            // 특정 팝업에 대한 리뷰를 서비스 계층에서 조회
            List<PopupReviewDTO> reviews = popupReviewService.selectReviewsByPopupId(popupId);

            // 모델에 조회한 리뷰 목록을 추가
            model.addAttribute("reviews", reviews);

            // 평균 별점 계산 (선택사항)
            double averageRating = reviews.stream()
                    .mapToInt(PopupReviewDTO::getRating)
                    .average()
                    .orElse(0.0);
            model.addAttribute("averageRating", averageRating);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "리뷰 조회 중 오류가 발생했습니다.");
        }
        return "popup/popupView"; // 리뷰를 보여줄 템플릿 경로 (예: reviews.html)
    }
}
