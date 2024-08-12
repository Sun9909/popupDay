package flower.popupday.point.controller;

import flower.popupday.point.dto.PointDTO;
import flower.popupday.point.service.PointService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.*;

@Controller("pointController")
public class PointControllerImpl implements PointController{

    @Autowired
    private PointService pointService;

    @Autowired
    private PointDTO pointDTO;

    @Override
    @RequestMapping("/point/pointShop.do")
    public ModelAndView pointShop(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List pointList = pointService.pointList();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("point/pointShop");
        mav.addObject("pointList", pointList);
        return mav;
    }

    @Override
    @RequestMapping("/point/goodsForm.do")
    public ModelAndView goodsForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("point/goodsForm");
        return mav;
    }

    @Override
    @RequestMapping("/point/addGoods.do")
    public ModelAndView addGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> goodsMap=new HashMap<String, Object>();
        Enumeration enu=multipartRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String name=(String) enu.nextElement();
            String value=multipartRequest.getParameter(name);
            goodsMap.put(name, value); // 이미지 파일 name 까지 집어넣음
        }

        String image_file_name=(String)goodsMap.get("image_file_name");
        String product_name=(String)goodsMap.get("product_name");
        int product_price= Integer.parseInt((String) goodsMap.get("product_price"));
        pointDTO.setProduct_name(product_name);
        pointDTO.setProduct_price(product_price);
        pointDTO.setImage_file_name(image_file_name);
        int pointNo= pointService.addGoods(pointDTO);

        ModelAndView mav = new ModelAndView("redirect:/point/pointShop.do?pointNo=" + pointNo);
        return mav;
    }

    @Override
    @RequestMapping("/point/modifyGoods.do")
    public ModelAndView  modifyGoods(@RequestParam("shop_id") int shop_id,
                                     HttpServletRequest request, HttpServletResponse response)throws Exception{
        List moGoodsList = pointService.getGoodsContent(shop_id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("moGoodsList", moGoodsList);
        mav.setViewName("point/goodsModify");
        return mav;
    }

    @Override
    @RequestMapping("/point/removeGoods.do")
    public ModelAndView removeGoods(@RequestParam("shop_id") int shop_id,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        pointService.removeGoods(shop_id);
        ModelAndView mav = new ModelAndView("redirect:/point/pointShop.do");
        return mav;
    }

}
