package flower.popupday.point.controller;

import flower.popupday.point.service.PointService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller("pointController")
public class PointControllerImpl implements PointController{

    @Autowired
    private PointService pointService;

    @Override
    @RequestMapping("/point/pointShop.do")
    public ModelAndView pointShop(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List pointList = pointService.pointList();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("point/pointShop");
        mav.addObject("pointList", pointList);
        return mav;
    }
}
