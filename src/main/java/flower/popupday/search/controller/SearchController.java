package flower.popupday.search.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

// 검색 컨트롤러 인터페이스 정의
public interface SearchController {
    // 검색 메서드 선언. 'query' 파라미터와 'model' 객체를 받아 'ModelAndView'를 반환
    ModelAndView search(@RequestParam("query") String query, Model model);
}
