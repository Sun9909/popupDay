package flower.popupday.admin.controller;

import flower.popupday.admin.dto.AdminDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface AdminController {
    public ModelAndView adminPage(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //관리자 - 회원 관리
   // public ModelAndView memberShip(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView memberShip(@RequestParam(value = "section", required = false) String _section,
                                   @RequestParam(value = "pageNum", required = false) String _pageNum, //매개변수 Section,pageNum을 받으며 값이 없으면 기본적으로 null이 됨.
                                   HttpServletRequest request, HttpServletResponse response) throws DataAccessException ;

    public ModelAndView memberModify(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView updateMember(@ModelAttribute("adminDTO") AdminDTO adminDTO, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView delMember(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
