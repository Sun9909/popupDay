package flower.popupday.search.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface SearchController {
    ModelAndView search(@RequestParam("query") String query,
                        @RequestParam("searchType") String searchType,
                        Model model);

    ModelAndView searchPopupsByDate(@RequestParam("selectedDateAdded") String selectedDateAdded, Model model);
}
