package flower.popupday.search.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface SearchController {
    ModelAndView search(@RequestParam("query") String query, Model model);
}
