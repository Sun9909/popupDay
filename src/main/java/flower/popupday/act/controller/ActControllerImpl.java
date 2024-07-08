package flower.popupday.act.controller;

import com.springboot.act.dto.ActDTO;
import com.springboot.act.service.ActService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller("ActController")
public class ActControllerImpl implements ActController{

    @Autowired
    private ActService actService;

    @Autowired
    private ActDTO actDTO;

    @Override
    @GetMapping("/modify/loginModify.do")
    public ModelAndView loginModify(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        actDTO=actService.findMember(id); // 회원정보 찾아서 memberdto에 넘김
        ModelAndView mav=new ModelAndView("/modify/loginModify");
        mav.addObject("member",actDTO); // 서비스에서 한사람의 자료를 가져온걸 member에 넘겨 포워딩 (수정폼에 수정데이터 보여
        return mav;
    }

    @Override
    @GetMapping("/modify/pwdModify.do")
    public ModelAndView pwdModify(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        actDTO=actService.findMember(id); // 회원정보 찾아서 memberdto에 넘김
        ModelAndView mav=new ModelAndView("/modify/pwdModify");
        mav.addObject("member",actDTO); // 서비스에서 한사람의 자료를 가져온걸 member에 넘겨 포워딩 (수정폼에 수정데이터 보여
        return mav;
    }
}


