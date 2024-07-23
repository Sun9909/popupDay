package flower.popupday.search.controller;

import flower.popupday.popup.dto.PopupDTO;
import flower.popupday.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller // 이 클래스가 Spring MVC의 컨트롤러임을 나타냅니다.
@RequestMapping("/search") // 이 컨트롤러가 "/search" 경로와 매핑됨을 나타냅니다.
public class SearchControllerImpl implements SearchController {

    @Autowired // Spring이 SearchService를 자동으로 주입하도록 합니다.
    private SearchService searchService;

    @GetMapping // 이 메서드가 GET 요청을 처리하도록 합니다.
    public ModelAndView search(@RequestParam("query") String query, @RequestParam("searchType") String searchType, Model model) {
        // 요청 매개변수 "query"와 "searchType"을 받아옵니다. Model 객체를 통해 데이터를 뷰로 전달합니다.
        List<PopupDTO> results; // 검색 결과를 담을 리스트를 선언합니다.
        if ("hashtag".equals(searchType)) {
            // searchType이 "hashtag"이면 searchPopupsByHashTag 메서드를 호출하여 검색합니다.
            results = searchService.searchPopupsByHashTag(query);
        } else {
            // 그렇지 않으면 searchPopupsByWord 메서드를 호출하여 검색합니다.
            results = searchService.searchPopupsByWord(query);
        }
        model.addAttribute("results", results); // 검색 결과를 모델에 추가합니다.
        return new ModelAndView("/popup/searchList"); // 검색 결과 페이지로 이동합니다.
    }
}