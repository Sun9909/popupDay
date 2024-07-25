package flower.popupday.search.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

// 검색 컨트롤러 인터페이스 정의
public interface SearchController {
    // 검색 기능을 구현할 메서드의 시그니처를 정의합니다.
    ModelAndView search(@RequestParam("query") String query,
                        @RequestParam("searchType") String searchType,
                        Model model);

    String searchPopupsByDate(@RequestParam("selectedDateAdded") String selectedDateAdded, Model model);

}