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
import java.util.logging.Logger;

@Controller
@RequestMapping("/search")
public class SearchControllerImpl implements SearchController {

    @Autowired
    private SearchService searchService;

    private static final Logger logger = Logger.getLogger(SearchControllerImpl.class.getName());

    @Override
    @GetMapping
    public ModelAndView search(@RequestParam("query") String query, @RequestParam("searchType") String searchType, Model model) {
        List<PopupDTO> results;
        if ("hashtag".equals(searchType)) {
            results = searchService.searchPopupHasTag(query);
        } else {
            results = searchService.searchPopupsByWord(query);
        }
        model.addAttribute("searchResults", results);
        return new ModelAndView("popup/searchList");
    }

    @Override
    @RequestMapping("/searchPopups")
    public String searchPopupsByDate(@RequestParam("selectedDateAdded") String selectedDateAdded, Model model) {
        // 입력 받은 날짜 형식이 '2024-07-24'과 같은지 확인
        System.out.println("Received date: " + selectedDateAdded);

        // 형식을 SQL 형식으로 변환
        Date selectedDate = Date.valueOf(selectedDateAdded);

        List<PopupDTO> searchResults = searchService.searchPopupsByDate(selectedDate);
        model.addAttribute("searchResults", searchResults);
        return "popup/searchList";
    }

    // 오늘 날짜를 기준으로 팝업 상태별 검색
    @RequestMapping("/searchPopupsWithStatus")
    public String searchPopupsWithStatus(@RequestParam("selectedDateAdded") String selectedDateAdded, Model model) {
        // 오늘 날짜를 SQL 형식으로 변환
        Date today = Date.valueOf(selectedDateAdded);
        logger.info("Searching popups with date: " + today);

        List<PopupDTO> finishedPopups = searchService.searchFinishedPopups(today);
        List<PopupDTO> ongoingPopups = searchService.searchOngoingPopups(today);
        List<PopupDTO> upcomingPopups = searchService.searchUpcomingPopups(today);

        logger.info("Finished popups count: " + finishedPopups.size());
        logger.info("Ongoing popups count: " + ongoingPopups.size());
        logger.info("Upcoming popups count: " + upcomingPopups.size());

        model.addAttribute("finishedPopups", finishedPopups);
        model.addAttribute("ongoingPopups", ongoingPopups);
        model.addAttribute("upcomingPopups", upcomingPopups);
        return "popup/searchList";
    }


}
