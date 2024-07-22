package flower.popupday.search.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

// SearchController 인터페이스 선언
public interface SearchController {
    // search 메서드 선언: query 파라미터를 받아서 모델에 추가하고, ModelAndView를 반환
    ModelAndView search(@RequestParam("query") String query, Model model);
}
