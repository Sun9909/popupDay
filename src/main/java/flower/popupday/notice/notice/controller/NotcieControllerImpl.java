package flower.popupday.notice.notice.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.notice.notice.dao.NoticeDAO;
import flower.popupday.notice.notice.dto.NoticeDTO;
import flower.popupday.notice.notice.dto.NoticeimageDTO;
import flower.popupday.notice.notice.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.*;


@Controller("notcieController")
public class NotcieControllerImpl implements NoticeController {

    //이미지 파일을 저장할 디렉토리 경로
    private static String ARRICLE_IMG_REPO = "/path/to/image/repo";

    // NoticeService 인스턴스를 주입받음
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeDTO noticeDTO;
    @Autowired
    private NoticeDAO noticeDAO;
    @Autowired
    private HttpSession httpSession;

    // 관리자 권한 확인 메서드
    private boolean isAdmin(HttpSession session){
        // 세션에서 로그인 정보를 가져옴
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("member");
        // 로그인 정보가 없거나 관리자가 아닌 경우 false 반환 즉, admin인지 확인
        return loginDTO != null && "admin".equals(loginDTO.getRole());
    }

    //공지사항 목록을 보여주는 메서드
    @Override
    @RequestMapping("/notice/noticeList.do") // 메인 화면에서 공지사항으로 이동했을때 매핑이름
    public ModelAndView noticeList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false) //매개변수 section,pageNum을 받으며 값이 없으면 기본적으로 null이 됨.
    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws DataAccessException {
        int section = Integer.parseInt((_section == null) ? "1" : _section); // '_section'이 null이면 'section'을 1로 설정하고 그렇지 않으면, '_section'의 값을 정수로 변환하여 'section'에 저장
        int pageNum = Integer.parseInt((_pageNum == null) ? "1" : _pageNum); // 위와 동일함.
        Map<String, Integer> pageingMap = new HashMap<String, Integer>(); // 'section','pageNum'을 저장할 맵을 생성
        pageingMap.put("section", section); // 1 맵에 section 값을 저장
        pageingMap.put("pageNum", pageNum); // 1 맵에 pangNum 값을 저장
        Map noticeMap = noticeService.noticeList(pageingMap); // 서비스에서 글목록 받아오기(공지사항 목록을 받아옴)
        noticeMap.put("section", section); // noticMap에 section 값을 추가함
        noticeMap.put("pageNum", pageNum); // noticMap에 pageNum 값을 추가함

        // Debugging 로그 추가
        System.out.println("noticeMap: " + noticeMap);

        ModelAndView mav = new ModelAndView(); //ModelAndView 객체를 생성
        mav.setViewName("/notice/notice"); // 이 뷰로 이동
        mav.addObject("noticeMap", noticeMap); // notice을 mav에 추가하여 뷰로 전달 (글목록 넘겨줌)
        return mav; // 객체를 변환하여 뷰로 포워딩
    }

    //공지사항 작성 폼으로 이동하는 메서드
    @Override
    @RequestMapping("/notice/noticeForm.do") // 공지사항 글쓰기 폼으로 이동
    public ModelAndView noticeForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //세션에서 로그인 정보를 가져온
        HttpSession session = request.getSession();

        //관리자 권한확인
        if(!isAdmin(session)) {
            return  new ModelAndView("redirect:/notice/noticeList.do");//관리자가 아니면 공지사항 목록 페이로 리디렉션
        }
        ModelAndView mav = new ModelAndView(); //ModelAndView 객체 생성
        mav.setViewName("/notice/noticeForm"); //뷰 이름 설정
        return mav;
    }


    //글쓰기 + 이미지 추가
    @Override
    @RequestMapping("/admin/addNotice.do")
    //addArticle메서드는(adminNotice.html(<여기안에있음)) MultipartHttpServletRequest 객체를 사용하여 다중 파일 업로드 처리.
    public ModelAndView addNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {

        //세션에서 로그인 정보를 가져온
        HttpSession session = multipartRequest.getSession();

        //관리자 권한확인
        if(!isAdmin(session)) {
            return new ModelAndView("redirect:/notice/noticeList.do");
        }

        //인코딩 설정 및 초기화
        String imageFileName = null; //업로드 된 이미지 파일 이름을 저장할 변수
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> noticeMap = new HashMap<String, Object>(); // articleMap 게시물 데이터를 저장할 hasMap
        Enumeration enu = multipartRequest.getParameterNames(); // 모든 매개변수 이름을 가져옴

        // 요청 매개변수 읽기 및 저장 (모든 매개변수를 돌면서, articleMap에 저장
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String value = multipartRequest.getParameter(name);
            noticeMap.put(name, value);
        }//while end

        List<String> fileList = multiFileUpload(multipartRequest); //파일 업로드 처리
        List<NoticeimageDTO> imageFileList = new ArrayList<>(); //업로드된 이미지 파일 리스트 초기화

        //이미지 파일 정보 저장
        if (fileList != null && fileList.size() != 0) { // fileList가 비어 있지 않을 때  = if (fileList != null && !fileList.isEmpty())
            for (String fileName : fileList) {
                NoticeimageDTO noticeimageDTO = new NoticeimageDTO();
                noticeimageDTO.setImage_file_name(fileName);
                imageFileList.add(noticeimageDTO); // NoticeimageDTO 객체에 저장
            }
            noticeMap.put("imageFileList", imageFileList); //이미지 파일 리스트를 맵에 저장
        }

//        //세션에서 사용자 정보 가져오기(로그인 해서 들어가야함.)
//  이건 사용자니깐 뺴는게 맞는거 같음 지여나~
//        HttpSession session = multipartRequest.getSession();
//        // 회원정보DTO user테이블에 있는 아이디를 가지고 와야함 회원정보? 아마??
//        LoginDTO loginDTO = (LoginDTO) session.getAttribute("member");
//        String user_id = String.valueOf(loginDTO.getId());
//        noticeMap.put("user_id", user_id);

        //게시글 추가 및 이미지 파일 이동
        try {
            long notice_id = noticeService.addNotice(noticeMap); // 메서드를 호출해서 DB에 추가 및 새로운 게시물 ID받아  notice_id에 저장
            if (imageFileList != null && imageFileList.size() != 0) { //!imageFileList.isEmpty()
                for (NoticeimageDTO noticeimageDTO : imageFileList) {
                    imageFileName = noticeimageDTO.getImage_file_name(); // imageFileList 각 항목을 돌면서 NoticeimageDTO 객체에 이미지 파일 이름을 가져옴
                    File srcFile = new File(ARRICLE_IMG_REPO + "\\temp\\" + imageFileName); //원본파일(임시) 경로지정
                    File destFile = new File(ARRICLE_IMG_REPO + "\\" + notice_id); //대상파일(최종) 경로 지정
                    FileUtils.moveToDirectory(srcFile, destFile, true); // //메서드를 사용하여 파일을 임시 디렉터리에 최종 저장 후 디렉터리 이동
                } //for end
            } //if end
        } catch (Exception e) {
            if (imageFileList != null && imageFileList.size() != 0) { //!imageFileList.isEmpty()
                for (NoticeimageDTO noticeimageDTO : imageFileList) {
                    imageFileName = noticeimageDTO.getImage_file_name();
                    File SrcFile = new File(ARRICLE_IMG_REPO + "\\temp\\" + imageFileList); //원본파일(임시) 경로 지정
                    SrcFile.delete(); // 예외 발생 시 임시 디렉터리 파일 삭제
                } //for end
            } //if end
            e.printStackTrace();
        }//cath end
        ModelAndView mav = new ModelAndView("redirect:/notice/noticeForm.do"); // 게시물 추가 및 파일 이동 작업 완료후, 관리자의 공지 목록 페이지로 리디렉션하도록 객체 생성.
        return mav;
    }// method end


    //여러개의 이미지 상세 글보기
    @RequestMapping("/notice/notice.do")
    public ModelAndView adminNoticeView(@RequestParam("notice_id") Long notice_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map noticeMap = noticeService.adminNoticeView(notice_id); //공지사항 상세정보를 가져옴
        ModelAndView mav = new ModelAndView(); // ModelAndView 객체 생성
        mav.setViewName("/notice/notice"); // 뷰 이름 설정
        mav.addObject("noticeMap", noticeMap);  // 공지사항 상세 정보를 뷰에 전달
        return mav;
    }

    @Override //수정
    @RequestMapping("/notice/modNotice.do")
    public ModelAndView modNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
        // 세션에서 로그인 정보를 가져옴
        HttpSession session = multipartRequest.getSession();
        // 관리자 권한 확인
        if (!isAdmin(session)) {
            // 관리자가 아니면 공지사항 목록 페이지로 리디렉션
            return new ModelAndView("redirect:/notice/noticeList.do");
        }

        String image_file_name = null;  // 파일 업로드 및 데이터 저장을 위한 초기 설정
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> noticeMap = new HashMap<>();   // 공지사항 데이터를 저장할 맵 초기화
        Enumeration enu = multipartRequest.getParameterNames();  // 요청 파라미터의 이름들을 열거형으로 가져옴
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String value = multipartRequest.getParameter(name);
            System.out.println(name + " : " + value);
            noticeMap.put(name, value);
        } // while end

            List<String> fileList = multiFileUpload(multipartRequest);// 여러 파일 업로드 처리
            String noticeNo = (String) noticeMap.get("noticeNo"); // 수정할 공지사항 번호를 맵에서 가져옴
            List<NoticeimageDTO> imageFileList = new ArrayList<>(); // 업로드된 이미지 파일 리스트 초기화
            int modityNumber = 0;

        // 업로드된 파일이 있을 경우 처리
            if(fileList != null && fileList.size() !=0) {
                for(String fileName:fileList) {
                    modityNumber++;
                    NoticeimageDTO noticeimageDTO = new NoticeimageDTO();
                    noticeimageDTO.setImage_file_name(fileName);

                    //noticeimageDTO.setImage_file_name(Integer.parseInt((Long) noticeMap.get("notice_id" + modityNumber)));
                    imageFileList.add(noticeimageDTO);
                }
                noticeMap.put("imageFileList", imageFileList); // 이미지 파일 리스트를 맵에 저장
            }

    //>이부부부누확인
            // 공지사항 ID를 맵에 저장
            noticeMap.put("notice_id", "notice_id");

            try {
                if (imageFileList != null && !imageFileList.isEmpty()) {
                    int cnt = 0;
                    for (NoticeimageDTO noticeimageDTO : imageFileList) {
                        cnt++;
                        image_file_name = noticeimageDTO.getImage_file_name();
                        if (image_file_name != null && image_file_name != "") {
                            File srcFile = new File(ARRICLE_IMG_REPO + "\\temp\\" + image_file_name);
                            File desDir = new File(ARRICLE_IMG_REPO + "\\" + noticeNo);
                            FileUtils.moveFileToDirectory(srcFile, desDir, true);
                            //String OrginalFileName=(String)noticeMap.get("OrginalFileName" + cnt);
                            // System.out.println("이전 이미지 " + OrginalFileName);
                            // File oldFile = new File(ARRICLE_IMG_REPO + "\\" + noticeNo + "\\" + OrginalFileName);
                        }
                    }//for end
                }//if end

        }catch (Exception e) {   // 에러 발생 시 임시 디렉토리에 있는 파일 삭제
			/*if(imageFileList != null && imageFileList.size() != 0) {
				for(NoticeimageDTO noticeimageDTO : imageFileList) { // 이미지 전부
					image_file_name = noticeimageDTO.getImage_file_name();
					File srcFile = new File(ARRICLE_IMG_REPO + "\\temp\\" + imageFileList);
					srcFile.delete(); // 오류 발생시 temp 이미지 삭제
				} // for end
			} // if end*/
                e.printStackTrace(); // 글쓰기 수행중 오류 temp에 있는 이미지가 붕뜸
            } // catch end
        // 공지사항 목록 페이지로 리디렉션
        ModelAndView mav=new ModelAndView("redirect:/notic/noticeList.do");
        return mav;
    }

   //이미지삭제
    @Override
    @PostMapping("/notice/removeNotice.do")
    public ModelAndView removeNotice(@RequestParam("notice_id") Long notice_id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
       noticeService.removeNotice((long) notice_id);
       File imgDir = new File(ARRICLE_IMG_REPO + " \\" + notice_id);
       if(imgDir.exists()) {
           FileUtils.deleteDirectory(imgDir); // 이 디렉토리(폴더)를 삭제
        }
        ModelAndView mav=new ModelAndView("redirect:/notice/noticeList.do"); // 글 삭제 후 redirect 로 글목록 포워딩
        return mav;
    }

    /* // 한개의 이미지파일 업로드 , 글 수정시(이미지 선택안하면) null 이 들어가서 이미지가 사라짐 업로드폴더에는 남아있음.
    public String fileUpoad(MultipartHttpServletRequest multipartrequest) throws Exception {
        String imageFileName = null;
        Iterator<String> fileNames = multipartrequest.getFileNames(); // 열거형 객체(여러개)
        while (fileNames.hasNext()) { // has.Next 파일 이름이 없을때 까지 돔
            String fileName = fileNames.next(); // 첨부한 이미지 파일 이름
            MultipartFile mFile = multipartrequest.getFile(fileName); // 파일 크기
            imageFileName = mFile.getOriginalFilename(); // 가져옴
            File file = new File(ARRICLE_IMG_REPO + "\\" + fileName); // 경로 저장
            if (mFile.getSize() != 0) { // 크기가 0인 이미지 거르기
                if (!file.exists()) { // exists 존재하는지(not 이라 존재 안할때) , EX ) 기존에 있던 이미지를 또 추가하면 안됨
                    if (file.getParentFile().mkdir()) { // mkdir 폴더 생성
                        file.createNewFile();
                    } // inner if end
                } // inner if end
                mFile.transferTo(new File(ARRICLE_IMG_REPO + "\\temp\\" + imageFileName)); // 파일 전달 (임시저장소에)
            } // if end
            //return fileList;
        } // while end
        return imageFileName;
    }*/

    // 여러개의 이미지파일 업로드
    public List<String> multiFileUpload(MultipartHttpServletRequest multipartRequest) throws Exception {
        List<String> fileList = new ArrayList<String>();
        Iterator<String> fileNames = multipartRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            MultipartFile mFile = multipartRequest.getFile(fileName);
            String originalFileName = mFile.getOriginalFilename();
            fileList.add(originalFileName);
            File file = new File(ARRICLE_IMG_REPO + "\\" + fileName);
            if (mFile.getSize() != 0) {
                if (!file.exists()) {
                    if (file.getParentFile().mkdirs()) {
                        file.createNewFile();
                    }
                }
                mFile.transferTo(new File(ARRICLE_IMG_REPO + "\\temp\\" + originalFileName));
            }
        }
        return fileList;


    } // class end
}

