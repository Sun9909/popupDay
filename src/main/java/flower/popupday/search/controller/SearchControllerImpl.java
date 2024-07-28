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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;

@Controller // 이 클래스가 Spring MVC의 컨트롤러임을 나타냅니다.
@RequestMapping("/search") // "/search" 경로에 대한 요청을 처리합니다.
public class SearchControllerImpl implements SearchController {

    @Autowired // SearchService 객체를 자동으로 주입합니다.
    private SearchService searchService;

    private static final Logger logger = LoggerFactory.getLogger(SearchControllerImpl.class);

    @Override
    @GetMapping // HTTP GET 요청을 처리합니다.
    public ModelAndView search(@RequestParam("query") String query, @RequestParam("searchType") String searchType, Model model) {
        List<PopupDTO> results; // 검색 결과를 담을 리스트입니다.
        if ("hashtag".equals(searchType)) { // 검색 유형이 해시태그인 경우
            logger.info("Searching by hashtag: {}", query); // 해시태그로 검색하는 로그를 기록합니다.
            results = searchService.searchPopupHasTag(query); // 해시태그로 팝업을 검색합니다.
        } else { // 검색 유형이 단어인 경우
            logger.info("Searching by word: {}", query); // 단어로 검색하는 로그를 기록합니다.
            results = searchService.searchPopupsByWord(query); // 단어로 팝업을 검색합니다.
        }
        model.addAttribute("searchResults", results); // 검색 결과를 모델에 추가합니다.
        logger.info("Search Results: {}", results); // 검색 결과를 로그에 기록합니다.

        // 종료된 팝업 데이터를 추가합니다.
        List<PopupDTO> finishedPopups = searchService.searchFinishedPopups();
        model.addAttribute("finishedPopups", finishedPopups);
        logger.info("Finished Popups Retrieved: {}", finishedPopups);

        // 진행 중인 팝업 데이터를 추가합니다.
        List<PopupDTO> ongoingPopups = searchService.searchOngoingPopups();
        model.addAttribute("ongoingPopups", ongoingPopups);
        logger.info("Ongoing Popups Retrieved: {}", ongoingPopups);

        // 예정된 팝업 데이터를 추가합니다.
        List<PopupDTO> upcomingPopups = searchService.searchUpcomingPopups();
        model.addAttribute("upcomingPopups", upcomingPopups);
        logger.info("Upcoming Popups Retrieved: {}", upcomingPopups);

        return new ModelAndView("popup/searchList", model.asMap()); // "popup/searchList" 뷰와 모델 데이터를 반환합니다.
    }

    @RequestMapping("/searchPopups") // "/searchPopups" 경로에 대한 요청을 처리합니다.
    public ModelAndView searchPopupsByDate(@RequestParam("selectedDateAdded") String selectedDateAdded, Model model) {
        logger.info("Received date: {}", selectedDateAdded); // 선택된 날짜를 로그에 기록합니다.
        Date selectedDate = Date.valueOf(selectedDateAdded); // 문자열 형식의 날짜를 Date 객체로 변환합니다.
        List<PopupDTO> searchResults = searchService.searchPopupsByDate(selectedDate); // 날짜로 팝업을 검색합니다.
        model.addAttribute("searchResults", searchResults); // 검색 결과를 모델에 추가합니다.
        logger.info("Search Results by Date: {}", searchResults); // 검색 결과를 로그에 기록합니다.

        // 종료된 팝업 데이터를 추가합니다.
        List<PopupDTO> finishedPopups = searchService.searchFinishedPopups();
        model.addAttribute("finishedPopups", finishedPopups);
        logger.info("Finished Popups Retrieved: {}", finishedPopups);

        // 진행 중인 팝업 데이터를 추가합니다.
        List<PopupDTO> ongoingPopups = searchService.searchOngoingPopups();
        model.addAttribute("ongoingPopups", ongoingPopups);
        logger.info("Ongoing Popups Retrieved: {}", ongoingPopups);

        // 예정된 팝업 데이터를 추가합니다.
        List<PopupDTO> upcomingPopups = searchService.searchUpcomingPopups();
        model.addAttribute("upcomingPopups", upcomingPopups);
        logger.info("Upcoming Popups Retrieved: {}", upcomingPopups);

        return new ModelAndView("popup/searchList", model.asMap()); // "popup/searchList" 뷰와 모델 데이터를 반환합니다.
    }

    @RequestMapping("/searchByDate") // "/searchByDate" 경로에 대한 요청을 처리합니다.
    public ModelAndView searchPopupsByDate(Model model) {
        List<PopupDTO> finishedPopups = searchService.searchFinishedPopups(); // 종료된 팝업을 검색합니다.
        logger.info("Finished Popups Retrieved: {}", finishedPopups); // 종료된 팝업을 로그에 기록합니다.

        List<PopupDTO> ongoingPopups = searchService.searchOngoingPopups(); // 진행 중인 팝업을 검색합니다.
        logger.info("Ongoing Popups Retrieved: {}", ongoingPopups); // 진행 중인 팝업을 로그에 기록합니다.

        List<PopupDTO> upcomingPopups = searchService.searchUpcomingPopups(); // 예정된 팝업을 검색합니다.
        logger.info("Upcoming Popups Retrieved: {}", upcomingPopups); // 예정된 팝업을 로그에 기록합니다.

        model.addAttribute("finishedPopups", finishedPopups); // 종료된 팝업을 모델에 추가합니다.
        model.addAttribute("ongoingPopups", ongoingPopups); // 진행 중인 팝업을 모델에 추가합니다.
        model.addAttribute("upcomingPopups", upcomingPopups); // 예정된 팝업을 모델에 추가합니다.

        return new ModelAndView("popup/searchList", model.asMap()); // "popup/searchList" 뷰와 모델 데이터를 반환합니다.
    }
}
