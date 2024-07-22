package flower.popupday.popup.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.service.PopupService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.*;

@Controller("popupController")
public class PopupControllerImpl implements PopupController {

    private static String ARTICLE_IMG_REPO = "D:\\Sun\\fileupload";
    private static final long COOKIE_EXPIRY_DAYS = 1; // 쿠키 만료 시간

    @Autowired
    PopupService popupService;

    @Override
    @RequestMapping("/popup/popupAllList.do")
    public ModelAndView popupAllList(@RequestParam(value = "section", required = false) String _section,
                                     @RequestParam(value = "pageNum", required = false) String _pageNum,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        int section = Integer.parseInt((_section == null) ? "1" : _section);
        int pageNum = Integer.parseInt((_pageNum == null) ? "1" : _pageNum);
        Map<String, Integer> pagingMap = new HashMap<>();
        pagingMap.put("section", section); // 섹션
        pagingMap.put("pageNum", pageNum); // 페이지 번호
        Map<String, Object> popupMap = popupService.popupAllList(pagingMap); // 서비스에서 팝업 목록 받아오기

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/popup/popupAllList"); // View 이름 설정
        mav.addObject("popupInfoList", popupMap.get("popupInfoList")); // 팝업 정보 리스트를 View로 전달
        mav.addObject("totPopup", popupMap.get("totPopup")); // 전체 팝업 수를 View로 전달
        mav.addObject("section", section);
        mav.addObject("pageNum", pageNum);

        return mav; // ModelAndView 반환
    }

    @Override
    @GetMapping("/popup/popupForm.do")
    public ModelAndView popupForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/popup/popupForm");
        return mav;
    }

    @Override
    @PostMapping("/popup/addPopup.do")
    public ModelAndView addPopup(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
            throws Exception {
        multipartRequest.setCharacterEncoding("utf-8");
        // HTTP 요청에서 파라미터들을 Map으로 변환
        Map<String, Object> popupMap = new HashMap<>();
        Enumeration enu = multipartRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String[] value = multipartRequest.getParameterValues(name);
            if (value.length == 1) {
                // 단일 값인 경우
                popupMap.put(name, value[0]);
            } else {
                // 다중 값인 경우 (해시태그)
                List<String> valueList = Arrays.asList(value);
                popupMap.put(name, valueList);
            }
        }
        // 해시태그 처리 , id
        String[] hashTags = multipartRequest.getParameterValues("hash_tag");
        if (hashTags != null && hashTags.length > 0) {
            popupMap.put("hash_tag", Arrays.asList(hashTags));
        }

        // 파일 업로드 처리
        List<String> fileList = multiFileUpload(multipartRequest);
        List<ImageDTO> imageFileList = new ArrayList<>();
        if (fileList != null && !fileList.isEmpty()) {
            for (String fileName : fileList) {
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setImage_file_name(fileName);
                imageFileList.add(imageDTO);
            }
            popupMap.put("imageFileList", imageFileList);
        }

        // 세선에 있는 멤버정보를 가져와서 id 만 빼서 articleMap에 집어넣음
//        HttpSession session=multipartRequest.getSession();
//        LoginDTO loginDTO =(LoginDTO)session.getAttribute("member");
//        Long id= loginDTO.getId();
//        popupMap.put("id", id); // 세션을 이용해 로그인한 아이디 집어넣으면 됨

        try {
            // 팝업 추가 서비스 호출
            Long image_id = popupService.addPopup(popupMap);

            // 이미지 파일 이동 처리
            if (imageFileList != null && !imageFileList.isEmpty()) {
                for (ImageDTO imageDTO : imageFileList) {
                    String image_file_name = imageDTO.getImage_file_name();
                    File srcFile = new File(ARTICLE_IMG_REPO + "\\temp\\" + image_file_name);
                    File destDir = new File(ARTICLE_IMG_REPO + "\\" + image_id);
                    FileUtils.moveFileToDirectory(srcFile, destDir, true);
                }
            }
        } catch (Exception e) {
            // 글쓰기 수행 중 오류
            if (imageFileList != null && !imageFileList.isEmpty()) {
                for (ImageDTO imageDTO : imageFileList) {
                    String image_file_name = imageDTO.getImage_file_name();
                    File srcFile = new File(ARTICLE_IMG_REPO + "\\temp\\" + image_file_name);
                    // 오류 발생 시 temp폴더의 이미지를 모두 삭제
                    srcFile.delete();
                }
            }
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/popup/popupAllList.do");
    }

    // 여러 개의 이미지 파일 업로드
    private List<String> multiFileUpload(MultipartHttpServletRequest multipartRequest) throws Exception {
        List<String> fileList = new ArrayList<>();
        Iterator<String> fileNames = multipartRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            MultipartFile mFile = multipartRequest.getFile(fileName);
            String originalFileName = mFile.getOriginalFilename();
            fileList.add(originalFileName);
            File file = new File(ARTICLE_IMG_REPO + "\\" + fileName);
            if (mFile.getSize() != 0) {
                if (!file.exists()) {
                    if (file.getParentFile().mkdirs()) {
                        file.createNewFile();
                    }
                }
                mFile.transferTo(new File(ARTICLE_IMG_REPO + "\\temp\\" + originalFileName));
            }
        }
        return fileList;
    }

    @RequestMapping("/popup/popupView.do")
    public ModelAndView popupView(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 쿠키를 이용하여 조회수를 업데이트
        Cookie[] cookies = request.getCookies();
        boolean hasViewed = false;
        if (cookies != null) {
            for (Cookie viewCookie : cookies) {
                if (viewCookie.getName().equals("popupView_" + popup_id)) {
                    long lastViewedTime = Long.parseLong(viewCookie.getValue()); // 쿠키 값을 조회 시간으로 변환
                    long currentTime = System.currentTimeMillis();
                    long oneDayInMillis = 24 * 60 * 60 * 1000; // 24시간을 밀리초로 변환

                    if (currentTime - lastViewedTime < oneDayInMillis) { // 현재시간 - 마지막 조회시간이 24시간이 넘어가면 수행
                        hasViewed = true;
                    } else {
                        // 쿠키가 만료되었으므로 업데이트
                        viewCookie.setValue(String.valueOf(currentTime)); // 쿠키 값을 현재 시간으로 설정
                        viewCookie.setMaxAge((int) (COOKIE_EXPIRY_DAYS * 24 * 60 * 60)); // 쿠키 만료 기간 설정
                        response.addCookie(viewCookie);
                    }
                }
            }
        }

        // 조회수 증가 처리
        if (!hasViewed) {
            popupService.updateHits(popup_id);
            // 새 쿠키 생성
            Cookie viewCookie = new Cookie("popupView_" + popup_id, String.valueOf(System.currentTimeMillis())); // 현재 시간을 밀리초로 반환
            viewCookie.setMaxAge((int) (COOKIE_EXPIRY_DAYS * 24 * 60 * 60)); // 쿠키 만료 기간 설정
            response.addCookie(viewCookie);
        }

        // 팝업 상세 조회
        Map popupMap = popupService.popupView(popup_id);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/popup/popupView");
        mav.addObject("popupMap", popupMap);
        return mav;
    }

    @PostMapping("/popup/addLike.do")
    @ResponseBody
    public Map<String, Object> addLike(HttpServletRequest request, @RequestBody Map<String, Long> requestBody) {
        Long popup_id = requestBody.get("popup_id");
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("member");
        Long user_id = loginDTO.getId();

        boolean success = popupService.addLike(user_id, popup_id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        return response;
    }

    @PostMapping("/popup/removeLike.do")
    @ResponseBody
    public Map<String, Object> removeLike(HttpServletRequest request, @RequestBody Map<String, Long> requestBody) {
        Long popup_id = requestBody.get("popup_id");
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("member");
        Long user_id = loginDTO.getId();

        boolean success = popupService.removeLike(user_id, popup_id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        return response;
    }

    @Override
    @GetMapping("/popup/checkLogin.do")
    public ResponseEntity<?> checkLoginStatus(HttpSession session) throws Exception{
        // 세션에서 로그인 정보 확인
        boolean isLoggedIn = session.getAttribute("loginDTO") != null;

        // JSON 형태로 응답 반환
        return ResponseEntity.ok(Map.of(
                "isLoggedIn", isLoggedIn
        ));
    }

}