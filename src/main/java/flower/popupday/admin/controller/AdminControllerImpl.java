package flower.popupday.admin.controller;

import flower.popupday.admin.dto.AdminDTO;
import flower.popupday.admin.service.AdminService;
import flower.popupday.login.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller("adminController")
public class AdminControllerImpl implements AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminDTO adminDTO;
    @Autowired
    private LoginDTO loginDTO;

    @Override
    @RequestMapping("/admin/admin.do")
    public ModelAndView adminPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //ModelAndView mav = new ModelAndView("/admin/admin");
        HttpSession session = request.getSession();
        //세션에서 loginDTO 가져오기
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");

        System.out.println(loginDTO.getUser_nikname());

//        session.setAttribute("admin", loginDTO);
//        session.setAttribute("isLogOn", true);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/admin/admin");
        mav.addObject("admin", loginDTO);
        return mav;
    }

    @RequestMapping("/admin/memberShip.do")
    public ModelAndView memberShip(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List membersList = adminService.memberShip();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/admin/memberShip");
        mav.addObject("membersList", membersList);
        return mav;
    }

    @Override
    @GetMapping("/admin/memberModify.do")
    public ModelAndView memberModify(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        adminDTO = adminService.findMember(id);
        ModelAndView mav = new ModelAndView("/admin/memberModify");
        mav.addObject("member", adminDTO);
        return mav;
    }

    @Override
    @PostMapping("/admin/updateMember.do")
    public ModelAndView updateMember(@ModelAttribute("adminDTO") AdminDTO adminDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        adminService.updateMember(adminDTO);
        ModelAndView mav = new ModelAndView("redirect:/admin/memberShip.do");
        return mav;
    }

    @Override
    @GetMapping("/admin/delMember.do")
    public ModelAndView delMember(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        adminService.delMember(id);
        ModelAndView mav = new ModelAndView("redirect:/admin/memberShip.do");
        return mav;
    }

//    @Override
//    @GetMapping("/admin/check-id")
//    @ResponseBody
//    public boolean checkId(@RequestParam("user_id") String user_id) {
//        return adminService.checkId(user_id);
//    }
//
//    @Override
//    @GetMapping("/admin/check-nkiname")
//    @ResponseBody
//    public boolean checkNikname(@RequestParam("user_nikname") String user_nikname) {
//        return adminService.checkNikname(user_nikname);
//    }
}
