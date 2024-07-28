
package flower.popupday.notice.qna.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.notice.qna.dto.QnaDTO;
import flower.popupday.notice.qna.service.QnaServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Long.parseLong;
import static java.lang.System.out;

@Controller("qnaController")
public class QnaControllerImpl implements QnaController {

    @Autowired
    private QnaServiceImpl qnaService;

    //QNA작성폼으로 이동
    @Override
    @RequestMapping("/notice/qnaForm.do")
    public ModelAndView qnaForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav= new ModelAndView();
        HttpSession session = request.getSession();

        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");
        if (loginDTO == null) {
            mav.setViewName("redirect:/login/loginForm.do");
            return mav;
        }

        mav.setViewName("notice/qnaForm");
        return mav;
    }

    // QNA리스트로 이동
    @Override
    @RequestMapping("/notice/qnaList.do") // "/notice/qnaList.do" 요청이 들어오면 이 메서드가 처리
    public ModelAndView qnaList(@RequestParam(value = "section", required = false) String _section,
                                @RequestParam(value = "pageNum", required = false) String _pageNum,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        // section,pageNum 파라미터가 없으면 기본값으로 1을 사용하여 정수로 변환
        int section =  Integer.parseInt((_section == null) ? "1" : _section);
        int pageNum =  Integer.parseInt((_pageNum == null) ? "1" : _pageNum);
        Map<String, Integer> pagingMap = new HashMap<>(); // section,pageNum을 저장 할 맵을 저장

        pagingMap.put("section", section); // 1 맵에 seciton 값을 추가 함
        pagingMap.put("pageNum", pageNum); // 1 맵에 pageNum 값을 추가 함

        Map qnaMap = qnaService.listQna(pagingMap); // 서비스에서 공지사항 글 목록 받아옴

        qnaMap.put("section", section); // noticeMap에 section 값을 추가 함
        qnaMap.put("pageNum", pageNum); // noticeMap에 pageNum 값을 추가 함

        // Debuggin 로그 추가(noticeMap(section,pageNum)이 잘 넘어오는지 확인)
        out.println("qnaMap: " + qnaMap);

        ModelAndView mav = new ModelAndView(); // ModelAndView 객체를 생성
        mav.setViewName("notice/qna"); // 이 뷰로 이동
        mav.addObject("qnaMap", qnaMap); // notice을 mav에 추가하여 뷰로 전달(글 목록을 넘겨줌)

        return mav;
    }

    //등록및 저장
    @Override
    @PostMapping("/notice/addQna.do")
    public ModelAndView addQna(HttpServletRequest request) throws Exception {
        try {
            request.setCharacterEncoding("UTF-8");

            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String category_name =request.getParameter("category_name");

            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("제목을 입력해야 합니다.");
            }

            HttpSession session = request.getSession();
            LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");
            Long userId = loginDTO.getId();

            QnaDTO qnaDTO = new QnaDTO();
            qnaDTO.setUser_id(userId);
            qnaDTO.setTitle(title);
            qnaDTO.setContent(content);
            qnaDTO.setCategory_name(category_name);

            qnaService.addQna(qnaDTO);
            return new ModelAndView("redirect:/notice/qnaList.do");
        } catch (Exception e) {
            // 예외 처리 (로그 기록, 에러 페이지 반환 등)
            throw new RuntimeException("Qna 추가 중 오류 발생");
        }
    }

    // 상세보기
    @Override
    @RequestMapping("/notice/qnaView.do")
    public ModelAndView qnaView(@RequestParam("qna_id") long qna_id,
                                RedirectAttributes rAttr,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception { // qna_id 매개변수로 받아 공지사항 글을 조회
        Map qnaView = qnaService.qnaView(qna_id); // qnaService qna_id 해당하는 공지사항 글을 조회하며 noticeMap에 조정

//        HttpSession session = request.getSession();
//        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");
//        Long loggedInUserId = loginDTO.getId();
//        String userRole = loginDTO.getRole().name();
//
//        QnaDTO qnaDTO = (QnaDTO) qnaView.get("qna");
//        Long usdr_id = qnaDTO.getUser_id();
//
//        // 작성자 또는 관리자인 경우에만 접근 허용
//        if (!loggedInUserId.equals(usdr_id) && !userRole.equals("관리자")) {
//            rAttr.addFlashAttribute("flashMessage", "작성자와 관리자만 글을 볼 수 있습니다");
//            rAttr.addFlashAttribute("flashType", "error");
//            return new ModelAndView("redirect:/notice/qnaList.do");
//        }
//
//        ModelAndView mav = new ModelAndView(); // ModelAndView 객체 생성
//        mav.setViewName("/notice/qnaView"); // 뷰 이름 설정
//        mav.addObject("qnaView", qnaView); // "noticeMap"이라는 이름으로 ModelAndView 객체에 추가
//
//        return mav; // ModelAndView 객체를 반환
//
//    }
        try {
            qnaView = qnaService.qnaView(qna_id);
        } catch (Exception e) {
            e.printStackTrace();
            rAttr.addFlashAttribute("flashMessage", "문제 발생: " + e.getMessage());
            rAttr.addFlashAttribute("flashType", "error");
            return new ModelAndView("redirect:/notice/qnaList.do");
        }

        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");
        if (loginDTO == null) {
            rAttr.addFlashAttribute("flashMessage", "작성자와 관리자만<br>글을 볼 수 있습니다");
            rAttr.addFlashAttribute("flashType", "error");
            return new ModelAndView("redirect:/notice/qnaList.do");
        }

        Long loggedInUserId = loginDTO.getId();
        String userRole = loginDTO.getRole().name();

        QnaDTO qnaDTO = (QnaDTO) qnaView.get("qna");
        if (qnaDTO == null) {
            rAttr.addFlashAttribute("flashMessage", "Q&A 정보를 찾을 수 없습니다.");
            rAttr.addFlashAttribute("flashType", "error");
            return new ModelAndView("redirect:/notice/qnaList.do");
        }

        Long usdr_id = qnaDTO.getUser_id();

        // 작성자 또는 관리자인 경우에만 접근 허용
        if (!loggedInUserId.equals(usdr_id) && !userRole.equals("관리자")) {
            rAttr.addFlashAttribute("flashMessage", "작성자와 관리자만<br>글을 볼 수 있습니다");
            rAttr.addFlashAttribute("flashType", "error");
            return new ModelAndView("redirect:/notice/qnaList.do");
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("notice/qnaView");
        mav.addObject("qnaView", qnaView);

        return mav;
    }



    //수정반영하기
    @Override
    @RequestMapping("/notice/modQna.do")
    public ModelAndView modQna(@RequestParam("qna_id") long qna_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String title = request.getParameter("title"); //title 파라미터 값을 가져와 title 변수에 저장
        String content = request.getParameter("content");

        // QnaDTO 객체 생성 및 필드 설정
        QnaDTO qnaDTO = new QnaDTO(); // 새 객체를 생성
        qnaDTO.setTitle(title); // qnaDTO 객체의 title 필드를 HTTP 요청에서 받은 title 값으로 설정
        qnaDTO.setContent(content);
        qnaDTO.setQna_id(qna_id);


        // 서비스 호출
        qnaService.modQna(qnaDTO); // modQna메서들 호출 (qnaDTO :title,contetn, qna_id)를 가져옴

        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
      return mav;
   }



    //삭제하기
    @Override
//    @RequestMapping("/notice/removeQna.do")
//    public ModelAndView removeQna(@RequestParam("qna_id") long qna_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        qnaService.removeQna(qna_id);
//        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
//        return mav;
//    }
    @RequestMapping(value = "/notice/removeQna.do", method = RequestMethod.POST)
    public ModelAndView removeQna(@RequestParam("qna_id") long qna_id, RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            qnaService.removeQna(qna_id);
            rAttr.addFlashAttribute("flashMessage", "Q&A 질문이 삭제되었습니다.");
            rAttr.addFlashAttribute("flashType", "success");
        } catch (Exception e) {
            rAttr.addFlashAttribute("flashMessage", "Q&A 질문 삭제 중 오류가 발생했습니다.");
            rAttr.addFlashAttribute("flashType", "error");
        }
        return new ModelAndView("redirect:/notice/qnaList.do");
    }

    // 답글 쓰기 화면 요청
    @Override
    @RequestMapping("/notice/answer.do")
    public ModelAndView answer(@RequestParam("qna_id") long qna_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        QnaDTO qna = qnaService.getQnaById(qna_id);
        mav.setViewName("qnaView");
        mav.addObject("qna", qna);
        return mav;
    }

    // 답글 저장
    @Override
    @RequestMapping("/notice/addAnswer.do")
    public ModelAndView addAnswer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String answer = request.getParameter("answer");
        Long qna_id = Long.valueOf(request.getParameter("qna_id"));

        out.println("qna_id : " + qna_id );
        out.println("answer : " + answer);

        // 제목이 비어있는지 검사
        if (answer == null || answer.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        // 로그인 정보 가져오기
        HttpSession session=request.getSession();
        LoginDTO loginDTO=(LoginDTO)session.getAttribute("loginDTO");
        Long user_id=loginDTO.getId();
        //qnaMap.put("id", id);

        // qnaDTO개체 생성 및설정
        QnaDTO qnaDTO = new QnaDTO();
        qnaDTO.setUser_id(user_id);
        qnaDTO.setQna_id(qna_id);
        qnaDTO.setAnswer(answer);
        //qnaMap.put("qnaDTO",qnaDTO);
        qnaService.addAnswer(qnaDTO);  //서비스 호출

        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
    }

    //답변 수정반영하기
    @Override
    @RequestMapping("/notice/modAnswer.do")
    public ModelAndView modAnswer(@RequestParam("qna_id") long qna_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String answer = request.getParameter("answer");

        // QnaDTO 객체 생성 및 필드 설정
        QnaDTO qnaDTO = new QnaDTO();
        qnaDTO.setQna_id(qna_id);
        qnaDTO.setAnswer(answer);

        // 서비스 호출
        qnaService.modAnswer(qnaDTO); // modAnswer 메서드 호출

        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
    }

    //답변 수정
    @Override
    @RequestMapping(value = "/notice/removeAnswer.do", method = RequestMethod.POST)
    public ModelAndView removedAnswer(@RequestParam("qna_id") long qna_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        qnaService.removeAnswer(qna_id);
        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
    }


}
