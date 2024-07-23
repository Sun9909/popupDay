package flower.popupday.search.controller;

import flower.popupday.popup.dto.PopupDTO;
import flower.popupday.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchControllerImpl implements SearchController {

    // SearchService를 자동으로 주입받음
    @Autowired
    private SearchService searchService;

    // SearchController 인터페이스에서 정의된 search 메서드를 구현
    @Override
    @GetMapping("/search") // /search 경로에 대한 GET 요청을 처리
    public ModelAndView search(@RequestParam("query") String query, Model model) {
        // 검색 서비스의 searchPopupsByHashTag 메서드를 호출하여 결과를 얻음
        List<PopupDTO> results = searchService.searchPopupsByHashTag(query);
        // 모델 객체에 검색 결과를 추가
        model.addAttribute("results", results);
        // 검색 결과를 포함한 모델과 함께 뷰 이름을 반환
        return new ModelAndView("/popup/searchList");
    }
}