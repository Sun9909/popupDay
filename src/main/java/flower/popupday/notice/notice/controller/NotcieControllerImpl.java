package flower.popupday.notice.notice.controller;

import flower.popupday.notice.notice.dao.NoticeDAO;
import flower.popupday.notice.notice.dto.NoticeDTO;
import flower.popupday.notice.notice.dto.NoticeimageDTO;
import flower.popupday.notice.notice.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.*;


@Controller("notcieController")
public class NotcieControllerImpl implements NoticeController {

    //저장할 곳 고정 값으로 지정
    private static String ARRICLE_IMG_REPO = "";

    // NoticeService 인스턴스를 주입받음
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeDTO noticeDTO;
    @Autowired
    private NoticeDAO noticeDAO;

    //페이징 처리 해보자
    //페이징 처리 해보자
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

    // 오프셋 계산 메서드
    //private int calculateOffset(int section, int pageNum) {
    //   return (section - 1) * 100 + (pageNum - 1) * 10;
    //}

    @Override
    @RequestMapping("/notice/noticeForm.do") // 공지사항 글쓰기 폼으로 이동
    public ModelAndView noticeForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("notice/noticeForm");
        return mav;
    }


    //글쓰기에 여러개 이미지 추가
    @Override
    @RequestMapping("/admin/addNotice.do")
    //addArticle메서드는(adminNotice.html(<여기안에있음)) MultipartHttpServletRequest 객체를 사용하여 다중 파일 업로드 처리.
    public ModelAndView addNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
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

        //파일 업로드 처리
        List<String> fileList = multiFileUpload(multipartRequest);
        List<NoticeimageDTO> imageFileList = new ArrayList<>();

        //이미지 파일 정보 저장
        if (fileList != null && fileList.size() != 0) { // fileList가 비어 있지 않을 때  = if (fileList != null && !fileList.isEmpty())
            for (String fileName : fileList) {
                NoticeimageDTO noticeimageDTO = new NoticeimageDTO();
                noticeimageDTO.setImage_file_name(fileName);
                imageFileList.add(noticeimageDTO); // NoticeimageDTO 객체에 저장
            }
            noticeMap.put("imageFileList", imageFileList);
        }

//        //세션에서 사용자 정보 가져오기
//        HttpSession session = multipartRequest.getSession();
//        // 회원정보DTO user테이블에 있는 아이디를 가지고 와야함 회원정보? 아마??
//        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
//        String id = memberDTO.getId();
//        noticeMap.put("id", id);
//
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
        Map noticeMap = noticeService.adminNoticeView(notice_id);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/notice/notice");
        mav.addObject("noticeMap", noticeMap);
        return mav;
    }

    @Override
    public ModelAndView modNotice(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
        String image_file_name = null;
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> noticeMap = new HashMap<>();
        Enumeration enu = multipartRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String value = multipartRequest.getParameter(name);
            System.out.println(name + " : " + value);
            noticeMap.put(name, value);
        } // while end

            List<String> fileList = multiFileUpload(multipartRequest);
            String noticeNo = (String) noticeMap.get("noticeNo");
            List<NoticeimageDTO> imageFileList = new ArrayList<>();
            int modityNumber = 0;

            //두개의 테이블을 동시에 사용
            if(fileList != null && fileList.size() !=0) {
                for(String fileName:fileList) {
                    modityNumber++;
                    NoticeimageDTO noticeimageDTO = new NoticeimageDTO();
                    noticeimageDTO.setImage_file_name(fileName);

                    noticeimageDTO.setImage_file_name(fileName);
                    // 오류나서 주석처리 했음
                    //noticeimageDTO.setImage_file_name(Integer.parseInt((Long) noticeMap.get("notice_id" + modityNumber)));
                    imageFileList.add(noticeimageDTO);
                }
                noticeMap.put("imageFileList", imageFileList);
            }
            noticeMap.put("notice_id", "kim");
            // 오류나서 주석처리 했음
            //(imageFileList != null && imageFileList.size() != 0)
            try  {
                int cnt=0;
                for(NoticeimageDTO noticeimageDTO : imageFileList) {
                    cnt++;
                    image_file_name = noticeimageDTO.getImage_file_name();
                    if(image_file_name != null && image_file_name != "") {
                        File srcFile = new File(ARRICLE_IMG_REPO + "\\temp\\" + image_file_name);
                        File desDir = new File(ARRICLE_IMG_REPO + "\\" + noticeNo);
                        FileUtils.moveFileToDirectory(srcFile, desDir, true);
                        String OrginalFileName=(String)noticeMap.get("OrginalFileName" + cnt);
                        System.out.println("이전 이미지 " + OrginalFileName);
                        File oldFile = new File(ARRICLE_IMG_REPO + "\\" + noticeNo + "\\" + OrginalFileName);
                    }
                }//for end
            //if end

        }catch (Exception e) { // 글쓰기 하다 오류나면 여기로 옴
			/*if(imageFileList != null && imageFileList.size() != 0) {
				for(NoticeimageDTO noticeimageDTO : imageFileList) { // 이미지 전부
					image_file_name = noticeimageDTO.getImage_file_name();
					File srcFile = new File(ARRICLE_IMG_REPO + "\\temp\\" + imageFileList);
					srcFile.delete(); // 오류 발생시 temp 이미지 삭제
				} // for end
			} // if end*/
                e.printStackTrace(); // 글쓰기 수행중 오류 temp에 있는 이미지가 붕뜸
            } // catch end
        ModelAndView mav=new ModelAndView("redirect:/notic/noticeList.do");
        return mav;
    }

    @Override
    public ModelAndView removeNotice(Long notice_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

    @Override
    public ModelAndView removeNotice(int noticeNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

    // 한개의 이미지파일 업로드 , 글 수정시(이미지 선택안하면) null 이 들어가서 이미지가 사라짐 업로드폴더에는 남아있음.
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
    }

    // 여러개의 이미지파일 업로드
    // 원래 public이 아니라 private 임
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

