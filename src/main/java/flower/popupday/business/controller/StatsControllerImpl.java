package flower.popupday.business.controller;

import flower.popupday.business.dto.HitsDTO;
import flower.popupday.business.dto.StatsDTO;
import flower.popupday.business.service.StatsService;
import flower.popupday.login.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("statsController")
public class StatsControllerImpl implements StatsController{

    @Autowired
    private StatsService statsService;

    @Override
    @RequestMapping("/business/StatsList.do")
    public ModelAndView StatsList(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<HitsDTO> count = statsService.statsCount(popup_id);//해당 팝업 조회수 가져오기
        List<StatsDTO> user_info = statsService.userStats(popup_id);

        Map statsMap = new HashMap<>();

        statsMap.put("count",count);
        statsMap.put("user_info",user_info);

        response.setContentType("application/json");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("busniess/statsList");
        mav.addObject("statsMap",statsMap);
        return mav;
    }
}
