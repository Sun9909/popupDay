package flower.popupday.admin.controller;

import flower.popupday.admin.dto.AdminDTO;
import flower.popupday.admin.service.AdminService;
import flower.popupday.login.dto.LoginDTO;
import flower.popupday.popup.service.PopupServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("adminController")
public class AdminControllerImpl implements AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminDTO adminDTO;
    @Autowired
    private LoginDTO loginDTO;
    @Autowired
    private PopupServiceImpl popupService;

    @Override
    @RequestMapping("/admin/admin.do")
    public ModelAndView adminPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //ModelAndView mav = new ModelAndView("/admin/admin");
        HttpSession session = request.getSession();
        //세션에서 loginDTO 가져오기
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");

        System.out.println(loginDTO.getUser_nickname());

//        session.setAttribute("admin", loginDTO);
//        session.setAttribute("isLogOn", true);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/admin");
        mav.addObject("admin", loginDTO);
        return mav;
    }

//    @RequestMapping("/admin/memberShip.do")
//    public ModelAndView memberShip(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List membersList = adminService.memberShip();
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("admin/memberShip");
//        mav.addObject("membersList", membersList);
//        return mav;
//    }


    @Override
    @RequestMapping("/admin/memberShip.do")
    public ModelAndView memberShip(@RequestParam(value = "section", required = false) String _section,
                                   @RequestParam(value = "pageNum", required = false) String _pageNum, //매개변수 Section,pageNum을 받으며 값이 없으면 기본적으로 null이 됨.
                                   HttpServletRequest request, HttpServletResponse response) throws DataAccessException {
        int section = Integer.parseInt((_section == null) ? "1" : _section); // '_section'이 null 이면 'section'을 1로 설정하고 그렇지 않으면 '_section'의 값을 정수로 변화하여 'section'에 저장
        int pageNum = Integer.parseInt((_pageNum == null) ? "1" : _pageNum); // 위와 동일한 내용

        Map<String, Integer> pagingMap = new HashMap<>(); // section,pageNum을 저장 할 맵을 저장

        pagingMap.put("section", section); // 1 맵에 seciton 값을 추가 함
        pagingMap.put("pageNum", pageNum); // 1 맵에 pageNum 값을 추가 함

        Map memberMap = adminService.memberShip(pagingMap);

        memberMap.put("section", section); // memberMap section 값을 추가 함
        memberMap.put("pageNum", pageNum); // memberMap pageNum 값을 추가 함

        ModelAndView mav = new ModelAndView(); // ModelAndView 객체를 생성
        mav.setViewName("admin/memberShip"); // 이 뷰로 이동
        mav.addObject("memberMap", memberMap); // memberMap mav에 추가하여 뷰로 전달(글 목록을 넘겨줌)

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
}
