package flower.popupday.admin.controller;

import flower.popupday.admin.dto.AdminDTO;
import flower.popupday.admin.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller("adminController")
public class AdminControllerImpl implements AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminDTO adminDTO;

    @GetMapping("/admin/memberShip.do")
    public ModelAndView memberShip(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List membersList = adminService.memberShip();
        ModelAndView mav = new ModelAndView();
        System.out.println(membersList);
        mav.setViewName("/admin/memberShip");
        mav.addObject("membersList", membersList);
        return mav;
    }

    @Override
    @GetMapping("/admin/delMember.do")
    public ModelAndView delMember(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        adminService.delMember(id);
        ModelAndView mav=new ModelAndView("redirect:/admin/memberShip.do");
        return mav;
    }
}
