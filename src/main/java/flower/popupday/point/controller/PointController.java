package flower.popupday.point.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public interface PointController {

    public ModelAndView pointShop(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView goodsForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView changeGoods(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView addGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

    public ModelAndView removeGoods(@RequestParam("shop_id") int shop_id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView modifyGoods(@RequestParam("shop_id") int shop_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
}