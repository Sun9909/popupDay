package flower.popupday.act.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface ActController {

    // 회원정보 수정하기
    public ModelAndView loginModify(@RequestParam("id") String id, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception;

    //  비밀번호 수정하기
    public ModelAndView pwdModify(@RequestParam("id") String id,HttpServletRequest request,
                                  HttpServletResponse response) throws Exception;
}
