package flower.popupday.search.controller;

import flower.popupday.search.dto.SearchDTO;
import flower.popupday.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// SearchControllerImpl 클래스 선언 및 @Controller 어노테이션 추가
@Controller
public class SearchControllerImpl implements SearchController {


    // SearchService를 자동 주입
    @Autowired
    private SearchService searchService;

    // @GetMapping 어노테이션을 사용하여 /search 경로에 대한 GET 요청을 매핑
    @Override
    @GetMapping("/search")
    public ModelAndView search(@RequestParam("query") String query, Model model) {
        // searchService를 사용하여 검색 결과를 가져옴
        List<SearchDTO> results = searchService.searchHashTags(query);
        // 모델에 검색 결과를 추가
        model.addAttribute("results", results);
        // search/results 뷰를 사용하여 ModelAndView를 반환
        return new ModelAndView("search/results");
    }
}

