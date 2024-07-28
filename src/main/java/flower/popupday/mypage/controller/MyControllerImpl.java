package flower.popupday.mypage.controller;


import flower.popupday.login.dto.LoginDTO;
import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import flower.popupday.mypage.service.MyService;
import flower.popupday.popup.dto.PopupDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

@Controller("myController")
public class MyControllerImpl implements MyController {

    @Autowired
    private MyService myService;

    //    @Autowired
//    private MyDTO myDTO;
    @Autowired
    private MyPopupDTO myPopupDTO;
    @Autowired
    private PopupDTO popupDTO;
//    @Autowired
//    private LoginDTO loginDTO;
//    @Autowired
//    private MyDTO myDTO;

    //마이페이지
    @Override
    @RequestMapping("/mypage/memberPage.do")
    public ModelAndView getName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();

        //세션에서 loginDTO 가져오기
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");

        // 세션에 myDTO 설정
        session.setAttribute("my", loginDTO);
        session.setAttribute("isLogOn", true);

        //값 잘 받아오는지 확인
//        System.out.println(loginDTO.getUser_nickname());
//        System.out.println(loginDTO.getRole());

        // 로그인된 사용자의 역할(role)에 따라 리다이렉트 설정
        if (loginDTO.getRole() == LoginDTO.Role.일반) {
            //System.out.println(loginDTO.getRole());
            mav.setViewName("redirect:/mypage/reviewCount.do"); // 리뷰 카운트 페이지로 리다이렉트
        }
        else if(loginDTO.getRole() == LoginDTO.Role.사업자) {
            //System.out.println(loginDTO.getRole());
            mav.setViewName("redirect:/mypage/businessPage.do");
        }
        else if (loginDTO.getRole() == LoginDTO.Role.관리자) {
            mav.setViewName("redirect:/admin/admin.do");
        }
        else {
            mav.setViewName("redirect:login/loginForm"); // 로그인 폼으로 유도
        }
        return mav;
    }

    @Override
    @RequestMapping("/mypage/reviewCount.do")
    public ModelAndView getCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");
        //myDTO=myService.getName(myDTO);
        Long reviewCount = myService.getReviewCount(loginDTO.getId()); // 리뷰 개수 조회

        //String recommentCount = myService.getreCommentCount(loginDTO.getUser_nickname()); //리뷰댓글
        //String popcommentCount = myService.getpopCommentCount(loginDTO.getUser_nickname()); //팝업댓글

        Long qnaCount = myService.getQnaCount(loginDTO.getId());
        // 일단 user_id가 안되는 이유 = 값을 가져갈때 user_tbl의 user_id를 가져감 , 그래서 조회가 안됨, review_tbl의
        // user_id(FK) 값을 조회해서 select 해서 값을 들고 가면됨
        ModelAndView mav = new ModelAndView("mypage/memberPage");
        mav.addObject("my", loginDTO);
        mav.addObject("reviewCount", reviewCount);
        //mav.addObject("recommentCount", recommentCount);
        //mav.addObject("popcommentCount", popcommentCount);
        mav.addObject("qnaCount", qnaCount);
        return mav;
    }

    //내 정보 수정 페이지로 이동
    @Override
    @RequestMapping("/modify/loginModify.do")
    public ModelAndView loginModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); //세션 가져오기(사용자 상태 유지를 위해)
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");    //loginDTO 속성으로 저장된 객체를 가져와 LoginDTO 타입으로 캐스팅. 사용자의 로그인 정보를 담고 있음

        MyDTO myDTO=myService.findMember(loginDTO.getId()); //사용자 id를 가져와 서비스의 findMember 메소드를 호출하여 MyDTO객체를 반환받음. 사용자의 상세 정보를 담고 있음
        ModelAndView mav = new ModelAndView("modify/loginModify"); // 새로운 ModelAndView 객체 생성. 포워딩?
        mav.addObject("myInfo", myDTO); //myDTO객체를 myInfo라는 이름으로 ModelAndView 객체에 추가. 이 데이터가 뷰에서 사용됨. 바인딩?
        return mav;
    }

    //자신의 정보를 수정한 후 저장하기
    @Override
    @RequestMapping("/mypage/updateLogin.do")
    public ModelAndView updateLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); //세션 가져오기
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");    //세션에서 loginDTO 가져오기

        //loginDTO.setId(sessionLoginDTO.getId());  //세션에서 가져온 id를 설정하여 보안 유지
        //MyDTO myDTO = new MyDTO();
        loginDTO.setUser_nickname(request.getParameter("user_nickname"));
        loginDTO.setName(request.getParameter("name"));
        loginDTO.setEmail(request.getParameter("email"));

        myService.updateLogin(loginDTO);   //loginDTO를 서비스로 전달
        session.setAttribute("loginDTO", loginDTO); //업데이트된 정보를 세션에 저장
        //System.out.println(loginDTO);
        //System.out.println(myDTO);

        ModelAndView mav = new ModelAndView("redirect:/mypage/memberPage.do");
        return mav;
    }

    //이메일 중복 확인
    @Override
    @RequestMapping("/mypage/check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email) {
        return myService.checkEmail(email);
    }

    //닉네임 중복 확인
    @Override
    @RequestMapping("/mypage/check-nickname")
    @ResponseBody
    public boolean checknickname(@RequestParam("user_nickname") String user_nickname) {
        return myService.checknickname(user_nickname);
    }

    @Override
    @RequestMapping("/modify/passwordModify.do")
    public ModelAndView passwordModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); //세션 가져오기(사용자 상태 유지를 위해)
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");    //loginDTO 속성으로 저장된 객체를 가져와 LoginDTO 타입으로 캐스팅. 사용자의 로그인 정보를 담고 있음

        MyDTO myDTO=myService.findMember(loginDTO.getId()); //사용자 id를 가져와 서비스의 findMember 메소드를 호출하여 MyDTO객체를 반환받음. 사용자의 상세 정보를 담고 있음
        ModelAndView mav = new ModelAndView("modify/passwordModify"); // 새로운 ModelAndView 객체 생성. 포워딩?
        mav.addObject("myInfo", myDTO); //myDTO객체를 myInfo라는 이름으로 ModelAndView 객체에 추가. 이 데이터가 뷰에서 사용됨. 바인딩?
        return mav;
    }

    @Override
    @RequestMapping("/mypage/updatePwd.do")
    public ModelAndView updatePwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");

        loginDTO.setPwd(request.getParameter("newpwd"));

        myService.updatePwd(loginDTO);
        session.setAttribute("loginDTO", loginDTO);

        ModelAndView mav = new ModelAndView("redirect:/mypage/memberPage.do");
        return mav;
    }

    @Override
    @RequestMapping("/mypage/dropMember.do")
    public ModelAndView dropMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");

        //loginDTO.setId(request.getParameter("id"));

        myService.dropMember(loginDTO);
        ModelAndView mav = new ModelAndView("redirect:/login/logout.do");
        return mav;
    }

    @Override
    @RequestMapping("/mypage/businessPage.do")
    public ModelAndView getBusiness(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");

//        System.out.println(loginDTO.getUser_id());
//        System.out.println(loginDTO.getUser_nickname());

        Long PopupCount = myService.getPopupCount(popupDTO.getUser_id());
        Long allPopupCount = myService.getAllPopupCount(popupDTO.getUser_id());

        ModelAndView mav = new ModelAndView("mypage/businessPage");
        mav.addObject("my", loginDTO);

        mav.addObject("popupCount", PopupCount);
        mav.addObject("allPopupCount", allPopupCount);

        //
        // mav.addObject("PopupCount", PopupCount);
        return mav;
    }

    @Override
    @RequestMapping("/mypage/memberLike.do")
    public ModelAndView memberLike(@RequestParam(value = "section", required = false) String _section,
                                   @RequestParam(value = "pageNum", required = false) String _pageNum,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        int section = Integer.parseInt((_section == null) ? "1" : _section);
        int pageNum = Integer.parseInt((_pageNum == null) ? "1" : _pageNum);

        int id = Integer.parseInt(request.getParameter("id"));

        Map<String, Integer> pagingMap = new HashMap<>();

        pagingMap.put("section", section); // 섹션
        pagingMap.put("pageNum", pageNum); // 페이지 번호
        pagingMap.put("id", id);
        Map<String, Object> popupMap = myService.myPopupLike(pagingMap); // 서비스에서 팝업 목록 받아오기

        ModelAndView mav = new ModelAndView();
        mav.setViewName("mypage/memberLike"); // View 이름 설정
        mav.addObject("popupMap", popupMap);
//        mav.addObject("popupLike", popupMap.get("popupLike")); // 팝업 정보 리스트를 View로 전달
//        mav.addObject("totPopup", popupMap.get("totPopup")); // 전체 팝업 수를 View로 전달
        mav.addObject("section", section);
        mav.addObject("pageNum", pageNum);

        // 디버깅 로그 추가
        System.out.println("popupLike: " + popupMap.get("popupLike"));
        System.out.println("totPopup: " + popupMap.get("totPopup"));

        return mav; // ModelAndView 반환
    }

    @Override
    @RequestMapping("/mypage/myReviewList.do")
    public ModelAndView reviewList(@RequestParam(value = "section", required = false) String _section,
                                   @RequestParam(value = "pageNum", required = false) String _pageNum,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        int section=Integer.parseInt((_section == null) ? "1" : _section);
        int pageNum=Integer.parseInt((_pageNum == null) ? "1" : _pageNum);
        int id = Integer.parseInt(request.getParameter("id"));

        Map<String, Integer> pagingMap=new HashMap<>();
        pagingMap.put("section", section); // 1
        pagingMap.put("pageNum", pageNum); // 1
        pagingMap.put("id", id);

        Map reviewMap = myService.reviewList(pagingMap);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("notice/review"); // 여기로감
        mav.addObject("reviewMap", reviewMap); // 글목록 넘겨줌
        mav.addObject("section", section);
        mav.addObject("pageNum", pageNum);

        return mav; // 포워딩
    }

    @Override
    @RequestMapping("/mypage/myQnaList.do")
    public ModelAndView qnaList(@RequestParam(value = "section", required = false) String _section,
                                @RequestParam(value = "pageNum", required = false) String _pageNum,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        int section =  Integer.parseInt((_section == null) ? "1" : _section);
        int pageNum =  Integer.parseInt((_pageNum == null) ? "1" : _pageNum);
        int id = Integer.parseInt(request.getParameter("id"));

        Map<String, Integer> pagingMap=new HashMap<>();
        pagingMap.put("section", section); // 1
        pagingMap.put("pageNum", pageNum); // 1
        pagingMap.put("id", id);

        Map qnaMap = myService.listQna(pagingMap); // 서비스에서 공지사항 글 목록 받아옴

        ModelAndView mav = new ModelAndView(); // ModelAndView 객체를 생성
        mav.setViewName("notice/qna"); // 이 뷰로 이동
        mav.addObject("qnaMap", qnaMap); // notice을 mav에 추가하여 뷰로 전달(글 목록을 넘겨줌)
        mav.addObject("section", section);
        mav.addObject("pageNum", pageNum);

        return mav;
    }

    @Override
    @RequestMapping("/mypage/unlikeClick.do")
    public ModelAndView unlikeClick(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");
        if (loginDTO != null) {
            Long id = loginDTO.getId();  // 세션에서 로그인한 사용자의 ID를 가져옴
            myService.unlikeClick(popup_id, id);  // ID를 서비스 메서드에 전달

            ModelAndView mav = new ModelAndView("redirect:/mypage/memberLike.do?id=" + id);
            return mav;
        } else {
            // 로그인 정보가 없을 경우 처리 (예: 로그인 페이지로 리다이렉트)
            ModelAndView mav = new ModelAndView("redirect:/login/login.do");
            return mav;
        }
    }

}