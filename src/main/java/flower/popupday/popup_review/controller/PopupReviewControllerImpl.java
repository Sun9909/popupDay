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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PopupReviewControllerImpl implements PopupReviewController {

    @Autowired
    private PopupReviewService popupReviewService;

    private static final Logger logger = LoggerFactory.getLogger(PopupReviewControllerImpl.class);

    // 리뷰 작성
    @Override
    @PostMapping("/popupComment/add")
    public String addReview(@RequestParam(value = "popup_id", required = true) Long popup_id,
                            @RequestParam(value = "user_id", required = true) Long user_id,
                            @RequestParam("content") String content,
                            RedirectAttributes redirectAttributes) {
        // 로그 출력
        System.out.println("Received popup_id: " + popup_id);
        System.out.println("Received user_id: " + user_id);
        System.out.println("Received content: " + content);

        try {
            // 파라미터 확인용 로그
            if (popup_id == null || user_id == null || content == null) {
                System.out.println("One of the parameters is null!");
                redirectAttributes.addFlashAttribute("errorMessage", "잘못된 요청입니다.");
                return "redirect:/popup/popupView";
            }

            // DTO에 데이터 전달
            PopupReviewDTO review = new PopupReviewDTO();
            review.setPopup_id(popup_id);
            review.setUser_id(user_id);
            review.setContent(content);

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
    @Override
    @GetMapping("/popupView")
    public String viewPopupReviews(@RequestParam("popup_id") long popup_id, Model model) {
        List<PopupReviewDTO> comments = popupReviewService.selectReviewsByPopupId(popup_id);
        model.addAttribute("comments", comments);
        model.addAttribute("popup_id", popup_id); // popup_id 값을 모델에 추가
        return "popup/popupView";
    }

}
