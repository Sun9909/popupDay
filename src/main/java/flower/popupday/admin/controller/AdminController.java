package flower.popupday.admin.controller;

import flower.popupday.admin.dto.AdminDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface AdminController {
    public ModelAndView adminPage(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //관리자 - 회원 관리
    public ModelAndView memberShip(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView memberModify(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView updateMember(@ModelAttribute("adminDTO") AdminDTO adminDTO, HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ModelAndView delMember(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    //아이디 중복 확인
//    boolean checkId(String user_id);
//
//    //닉네임 중복 확인
//    boolean checkNikname(String user_nikname);
}
