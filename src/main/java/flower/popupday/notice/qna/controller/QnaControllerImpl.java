
package flower.popupday.notice.qna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller("qnaController")
public class QnaControllerImpl implements QnaController {

    @Autowired
    private QnaDTO qnaDTO;

    @Autowired
    private QnaServiceImpl qnaService;



    //QNA작성폼으로 이동
    @Override
    @RequestMapping("/notice/qnaForm.do")
    public ModelAndView qnaForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav= new ModelAndView();
        mav.setViewName("/notice/qnaForm");
        return mav;
    }

    // QNA리스트로 이동
    @Override
    @RequestMapping("/notice/qnaList.do") // "/notice/qnaList.do" 요청이 들어오면 이 메서드가 처리
    public ModelAndView qnaList(@RequestParam(value = "secton", required = false) String _seciotn,
                                @RequestParam(value = "pageNum", required = false) String _pageNum,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        // secion,pageNum 파라미터가 없으면 기본값으로 1을 사용하여 정수로 변환
        int section =  Integer.parseInt((_seciotn == null) ? "1" : _seciotn);
        int pageNum =  Integer.parseInt((_pageNum == null) ? "1" : _pageNum);

        // 페이징된 QNA 리스트가져오기(section과 pageNum을 이용하여 페이징된 QNA 리스트를 서비스 계층에서 가져옴)
        List<QnaDTO> qnaList = qnaService.listQna(section,pageNum);

        // ModelAndView 객체 생성 및 설정
        ModelAndView mav =new ModelAndView();
        mav.setViewName("/notice/qna"); // 뷰 이름 설정
        mav.addObject("qnaList", qnaList); // qnaList 객체를 모델에 추가
        mav.addObject("section", section);
        mav.addObject("pageNum", pageNum);
        return mav;
    }

    @Override
    @PostMapping("/notice/addQna.do")
    public ModelAndView addQna(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");
        //Map<String, Object> qnaMap = new HashMap<>();
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        // 디버깅 출력
        System.out.println("title :" + title);
        System.out.println("content : " + content);

        // 제목이 비어있는지 검사
        if (title == null || title.trim().isEmpty()) {
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
        qnaDTO.setTitle(title);
        qnaDTO.setContent(content);
        //qnaMap.put("qnaDTO",qnaDTO);
        qnaService.addQna(qnaDTO);  //서비스 호출

        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
    }

    // 상세보기
    @Override
    @RequestMapping("/notice/qnaView.do")
    public ModelAndView qnaView(@RequestParam("qna_id") long qna_id, HttpServletRequest request, HttpServletResponse response) throws Exception { // notice_id를 매개변수로 받아 공지사항 글을 조회
        Map qnaView = qnaService.qnaView(qna_id); // noticService에서 notice_id에 해당하는 공지사항 글을 조회하며 noticeMap에 조정
        ModelAndView mav = new ModelAndView(); // ModelAndView 객체 생성
        mav.setViewName("/notice/qnaView"); // 뷰 이름 설정
        mav.addObject("qnaView", qnaView); // "noticeMap"이라는 이름으로 ModelAndView 객체에 추가

        return mav; // ModelAndView 객체를 반환
    }

    //수정반영하기
    @Override
    @RequestMapping("/notice/modQna.do")
    public ModelAndView modQna(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String qna_id = request.getParameter("qna_id");
        qnaDTO.setTitle(title);
        qnaDTO.setContent(content);
        qnaDTO.setQna_id(Long.parseLong(qna_id));
        qnaService.modQna(qnaDTO);
        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
    }

    //삭제하기
    @Override
    @RequestMapping("/notice/removeQna.do")
    public ModelAndView removeQna(@RequestParam("qna_id") long qna_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        qnaService.removeQna(qna_id);
        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
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
        String content = request.getParameter("content");
        String qnaIdParam  = request.getParameter("qna_id");

        System.out.println("qna_id : " + qnaIdParam );
        System.out.println("content : " + content);

        // qnaIdParam이 null이거나 빈 문자열인 경우 예외 처리
        if (qnaIdParam == null || qnaIdParam.trim().isEmpty()) {
            throw new IllegalArgumentException("Qna ID is required");
        }

        // qnaIdParam을 Long으로 변환
        Long qna_id;
        try {
            qna_id = Long.parseLong(qnaIdParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Qna ID format", e);
        }

        QnaDTO qna = qnaService.getQnaById(qna_id);

        //답변 상태설정
        qna.setAnswer(content);
        qna.setStatus(QnaDTO.Status.답변완료.name());

        //답변추가
        qnaService.addAnswer(qna);
        ModelAndView mav = new ModelAndView("redirect:/notice/qnaList.do");
        return mav;
    }
//    @Override
//    @PostMapping("/notice/addAnswer.do")
//    public void addAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            request.setCharacterEncoding("UTF-8");
//
//            String content = request.getParameter("content");
//            String qnaIdParam = request.getParameter("qna_id");
//
//            if (qnaIdParam == null || qnaIdParam.trim().isEmpty()) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().write("{\"error\":\"qna_id는 필수 입력값입니다.\"}");
//                return;
//            }
//
//            long qna_id;
//            try {
//                qna_id = Long.parseLong(qnaIdParam);
//            } catch (NumberFormatException e) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().write("{\"error\":\"유효하지 않은 qna_id입니다.\"}");
//                return;
//            }
//
//            QnaDTO qna = qnaService.getQnaById(qna_id);
//            if (qna == null) {
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                response.getWriter().write("{\"error\":\"존재하지 않는 QnA입니다.\"}");
//                return;
//            }
//
//            if (content == null || content.trim().isEmpty()) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().write("{\"error\":\"답변 내용은 필수 입력값입니다.\"}");
//                return;
//            }
//
//            qna.setAnswer(content);
//            qna.setStatus(QnaDTO.Status.답변완료.name());
//            qnaService.addAnswer(qna);
//
//            Map<String, Object> qnaView = qnaService.qnaView(qna_id);
//
//            String jsonResponse = objectMapper.writeValueAsString(qnaView);
//            response.getWriter().write(jsonResponse);
//
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("{\"error\":\"서버 오류가 발생했습니다.\"}");
//            e.printStackTrace(); // 로그에 예외를 기록합니다.
//        }
//    }



}