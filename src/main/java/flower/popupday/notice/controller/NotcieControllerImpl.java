package flower.popupday.notice.controller;

import flower.popupday.notice.dto.NoticeDTO;
import flower.popupday.notice.dto.NoticeimageDTO;
import flower.popupday.notice.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;


@Controller("notcieController")
public class NotcieControllerImpl implements NotcieController {

    //저장할 곳 고정 값으로 지정
    private static String ARRICLE_IMG_REPO = ""

    // NoticeService 인스턴스를 주입받음
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeDTO noticeDTO;

    //페이징 처리 해보자

    //글쓰기 폼으로 이동
    @Override
    @GetMapping("/admin/adminNotice.do") // 공지사항 폼으로 이동 (url이 호출될 때 해당 메서드 실행)
    //notice메서드는 ModelAndView 객체로 반환
    public ModelAndView notice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(); // ModeAndView 객체를 생성
        mav.setViewName("/dmin/adminNotice"); // 뷰이름을 설정 (여러개의 이미지와 글을 작성하는 폼)
        return  mav;
    }

    //글쓰기에 여러개 이미지 추가
    @Override
    @RequestMapping("/admin/adminNotice.do")
    //addArticle메서드는(adminNotice.html(<여기안에있음)) MultipartHttpServletRequest 객체를 사용하여 다중 파일 업로드 처리.
    public ModelAndView adminNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
        //인코딩 설정 및 초기화
        String imageFileName = null; //업로드 된 이미지 파일 이름을 저장할 변수
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> articleMap = new HashMap<String, Object>(); // articleMap 게시물 데이터를 저장할 hasMap
        Enumeration enu = multipartRequest.getParameterNames(); // 모든 매개변수 이름을 가져옴

        // 요청 매개변수 읽기 및 저장 (모든 매개변수를 돌면서, articleMap에 저장
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String value = multipartRequest.getParameter(name);
            articleMap.put(name, value);
        }//while end

        //파일 업로드 처리
        List<String> fileList = multiFileUpload(multipartRequest);
        List<NoticeimageDTO> imageFileList = new ArrayList<>();

        //이미지 파일 정보 저장
        if (fileList != null && fileList.size() != 0) { // fileList가 비어 있지 않을 때  = if (fileList != null && !fileList.isEmpty())
            for (String fileName : fileList) {
                NoticeimageDTO noticeimageDTO = new NoticeimageDTO();
                noticeimageDTO.setImageFileName(fileName);
                imageFileList.add(noticeimageDTO); // NoticeimageDTO 객체에 저장
            }
            articleMap.put("imageFileList", imageFileList);
        }

        //세션에서 사용자 정보 가져오기
        HttpSession session = multipartRequest.getSession();
        // 회원정보DTO user테이블에 있는 아이디를 가지고 와야함 회원정보? 아마??
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
        String id = memberDTO.getId();
        articleMap.put("id", id);

        //게시글 추가 및 이미지 파일 이동
        try {
            long notice_id = noticeService.adminNotice(articleMap); // 메서드를 호출해서 DB에 추가 및 새로운 게시물 ID받아  notice_id에 저장
            if (imageFileList != null && imageFileList.size() != 0) { //!imageFileList.isEmpty()
                for (NoticeimageDTO noticeimageDTO : imageFileList) {
                    imageFileName = noticeimageDTO.getImageFileName(); // imageFileList 각 항목을 돌면서 NoticeimageDTO 객체에 이미지 파일 이름을 가져옴
                    File srcFile = new File(ARRICLE_IMG_REPO + "\\temp\\" + imageFileName); //원본파일(임시) 경로지정
                    File destFile = new File(ARRICLE_IMG_REPO + "\\" + notice_id); //대상파일(최종) 경로 지정
                    FileUtils.moveToDirectory(srcFile, destFile, true); // //메서드를 사용하여 파일을 임시 디렉터리에 최종 저장 후 디렉터리 이동
                } //for end
            } //if end
        } catch (Exception e) {
            if(imageFileList != null && imageFileList.size() != 0) { //!imageFileList.isEmpty()
                for (NoticeimageDTO noticeimageDTO : imageFileList) {
                    imageFileName = noticeimageDTO.getImageFileName();
                    File SrcFile = new File(ARRICLE_IMG_REPO + "\\temp\\" + imageFileList); //원본파일(임시) 경로 지정
                    srcFile.delete(); // 예외 발생 시 임시 디렉터리 파일 삭제
                } //for end
            } //if end
            e.printStackTrace();
        }//cath end
        ModelAndView mav = new ModelAndView("redirect:/admin/adminNotice.do"); // 게시물 추가 및 파일 이동 작업 완료후, 관리자의 공지 목록 페이지로 리디렉션하도록 객체 생성.
        return mav;
    }// method end

}
