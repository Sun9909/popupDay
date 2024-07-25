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

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchControllerImpl implements SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ModelAndView search(@RequestParam("query") String query, @RequestParam("searchType") String searchType, Model model) {
        List<PopupDTO> results;
        if ("hashtag".equals(searchType)) {
            results = searchService.searchPopupsByHashTag(query);
        } else {
            results = searchService.searchPopupsByWord(query);
        }
        model.addAttribute("searchResults", results);
        return new ModelAndView("/popup/searchList");
    }

    @RequestMapping("/searchPopups")
    public String searchPopupsByDate(@RequestParam("selectedDateAdded") String selectedDateAdded, Model model) {
        // 입력 받은 날짜 형식이 '2024-07-24'과 같은지 확인
        System.out.println("Received date: " + selectedDateAdded);

        // 형식을 SQL 형식으로 변환
        Date selectedDate = Date.valueOf(selectedDateAdded);

        List<PopupDTO> searchResults = searchService.searchPopupsByDate(selectedDate);
        model.addAttribute("searchResults", searchResults);
        return "/popup/searchList";
    }
}
