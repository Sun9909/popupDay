package flower.popupday.point.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.point.dto.PointDTO;
import flower.popupday.point.service.PointService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
        HttpSession session=request.getSession();
        LoginDTO loginDTO=(LoginDTO)session.getAttribute("loginDTO");
        long id= loginDTO.getId();
        int userPoint = pointService.userPoint(id);
        List pointList = pointService.pointList();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("point/pointShop");
        mav.addObject("pointList", pointList);
        mav.addObject("userPoint",userPoint);
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
        mav.setViewName("point/goodsModify");
        mav.addObject("moGoodsList", moGoodsList);
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

    @Override
    @RequestMapping("/point/changeGoods.do")
    public ModelAndView changeGoods(HttpServletRequest request, HttpServletResponse response)throws Exception{
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        String image_file_name = request.getParameter("image_file_name");
        String product_name = request.getParameter("product_name");
        int product_price = Integer.parseInt(request.getParameter("product_price"));
        int product_count = Integer.parseInt(request.getParameter("product_count"));
        pointDTO.setShop_id(shop_id);
        pointDTO.setProduct_price(product_price);
        pointDTO.setProduct_name(product_name);
        pointDTO.setProduct_count(product_count);
        pointDTO.setImage_file_name(image_file_name);
        pointService.modGoods(pointDTO);
        ModelAndView mav = new ModelAndView("redirect:/point/pointShop.do");
        return mav;
    }

    @Override
    @RequestMapping("/point/pointUse.do")
    public ModelAndView pointUse(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String image_file_name = request.getParameter("image_file_name");
        int product_price = Integer.parseInt(request.getParameter("product_price"));
        int shop_id = Integer.parseInt(request.getParameter("shop_id"));
        int product_count = Integer.parseInt(request.getParameter("product_count"));

        HttpSession session=request.getSession();
        LoginDTO loginDTO=(LoginDTO)session.getAttribute("loginDTO");
        Long id=loginDTO.getId();
        Long tot_point = loginDTO.getTot_point();

        Map<String, Object> giftMap = new HashMap<>();
        giftMap.put("image_file_name", image_file_name);
        giftMap.put("product_price", product_price);
        giftMap.put("id", id);
        giftMap.put("tot_point", tot_point);
        giftMap.put("shop_id", shop_id);
        giftMap.put("product_count", product_count);

        pointService.getGiftList(giftMap);

        ModelAndView mav = new ModelAndView("redirect:/point/pointShop.do");
        return mav;
    }

}
