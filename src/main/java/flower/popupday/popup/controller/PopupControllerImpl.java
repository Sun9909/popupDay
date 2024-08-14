package flower.popupday.popup.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
import flower.popupday.popup.service.PopupService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(PopupService.class);
    //private static final int COOKIE_EXPIRY_DAYS = 7;
    private static final String RECENTLY_VIEWED_POPUPS_COOKIE_PREFIX = "recentlyViewedPopups_";

    @Autowired
    PopupService popupService;

    @Override
    @GetMapping("/main.do")
    public ModelAndView mainView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> mainMap = popupService.mainView();
        mav.addObject("mainMap", mainMap);
        mav.setViewName("main");

        // mainMap이 null이 아닌 경우에만 추가 처리
        if (mainMap != null) {
            Object bestPopupListObj = mainMap.get("bestPopupList");
            if (bestPopupListObj instanceof List<?>) {
                List<?> bestPopupList = (List<?>) bestPopupListObj;
                bestPopupList.forEach(popup -> {
                    if (popup instanceof PopupDTO) {
                        PopupDTO popupDTO = (PopupDTO) popup;
                        // 로그 출력용
                    }
                });
            }
        }

        return mav;
    }

    @Override
    @PostMapping("main/searchPopupHasTag")
    public ResponseEntity<Map<String, Object>> searchPopupHasTag(@RequestBody Map<String, String> request) {
        String hashTag = request.get("hash_tag");
        logger.info("Received search request with hash tag: {}", hashTag);

        Map<String, Object> response = new HashMap<>();
        try {
            List<PopupDTO> popups = popupService.searchPopupHasTag(hashTag);
            response.put("success", true);
            response.put("popups", popups);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/popup/selectPopupList.do")
    public ResponseEntity<Map<String, Object>> selectPopupList(@RequestBody Map<String, String> params) {
        String filter = params.get("filter");
        int pageNum = Integer.parseInt(params.getOrDefault("pageNum", "1"));
        int section = Integer.parseInt(params.getOrDefault("section", "1"));

        Map<String, Object> filterParams = new HashMap<>();
        filterParams.put("filter", filter);
        filterParams.put("pageNum", pageNum);
        filterParams.put("section", section);

        Map<String, Object> response = popupService.selectPopupList(filterParams);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequestMapping("/popup/popupAllList.do")
    public ModelAndView popupAllList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("popup/popupList"); // View 이름 설정
        return mav;
    }


    @Override
    @GetMapping("/popup/popupForm.do")
    public ModelAndView popupForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("popup/popupForm");
        return mav;
    }

    @Override
    @PostMapping("/popup/addPopup.do")
    public ModelAndView addPopup(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
            throws Exception {
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> popupMap = new HashMap<>();
        Enumeration enu = multipartRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String[] value = multipartRequest.getParameterValues(name);
            if (value.length == 1) { // 단일값
                popupMap.put(name, value[0]);
            } else {
                List<String> valueList = Arrays.asList(value);
                popupMap.put(name, valueList);
            }
        }
        // 해시태그 처리 , id
        String[] hashTags = multipartRequest.getParameterValues("hash_tag");
        if (hashTags != null && hashTags.length > 0) {
            popupMap.put("hash_tag", Arrays.asList(hashTags));
        }

        // 작성자 id 가져오기
        HttpSession session=multipartRequest.getSession();
        LoginDTO loginDTO=(LoginDTO)session.getAttribute("loginDTO");
        Long id=loginDTO.getId();
        popupMap.put("user_id", id);

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
            List<MultipartFile> mFiles = multipartRequest.getFiles(fileName); // 여러 파일 처리
            for (MultipartFile mFile : mFiles) {
                String originalFileName = mFile.getOriginalFilename();
                fileList.add(originalFileName);
                System.out.println("업로드 파일 이름: " + originalFileName);
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
        }
        return fileList;
    }

    @Override
    @RequestMapping("/popup/popupView.do")
    public ModelAndView popupView(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 쿠키를 이용하여 조회수를 업데이트
        Cookie[] cookies = request.getCookies();
        boolean hasViewed = false;
        if (cookies != null) {
            for (Cookie viewCookie : cookies) {
                if (viewCookie.getName().equals("popupView_" + popup_id)) {
                    long lastViewedTime = Long.parseLong(viewCookie.getValue());
                    long currentTime = System.currentTimeMillis();
                    long oneDayInMillis = 24 * 60 * 60 * 1000;

                    if (currentTime - lastViewedTime < oneDayInMillis) {
                        hasViewed = true;
                    } else {
                        viewCookie.setValue(String.valueOf(currentTime));
                        viewCookie.setMaxAge((int) (COOKIE_EXPIRY_DAYS * 24 * 60 * 60));
                        response.addCookie(viewCookie);
                    }
                }
            }
        }

        // 조회수 증가 처리
        if (!hasViewed) {
            popupService.updateHits(popup_id);
            Cookie viewCookie = new Cookie("popupView_" + popup_id, String.valueOf(System.currentTimeMillis()));
            viewCookie.setMaxAge((int) (COOKIE_EXPIRY_DAYS * 24 * 60 * 60));
            response.addCookie(viewCookie);
        }

        // 찜 기능 세션 가져오기
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");

        boolean loginCheck = loginDTO != null;
        Long id = loginCheck ? loginDTO.getId() : null;

        //로그인된 경우에만 최근 본 팝업 목록 처리
        if (loginCheck) {
            // 최근 본 팝업 목록을 쿠키에 저장
            String recentPopupsCookieName = "recentPopups_" + id;
            String recentPopups = "";

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(recentPopupsCookieName)) {
                        recentPopups = cookie.getValue();
//                        break; //최근 본 팝업 목록 쿠키를 찾았으므로 루프 종료
                    }
                }
            }

            // Base64로 인코딩된 최근 본 팝업 목록을 List로 변환
            List<Long> recentPopupIds = new ArrayList<>();
            if (!recentPopups.isEmpty()) {
                try {
                    //base64로 인코딩된 문자열을 디코딩하여 원래 문자열로 변환
                    byte[] decodedBytes = Base64.getDecoder().decode(recentPopups);
                    String decodedString = new String(decodedBytes);
                    //쉼표로 구분된 팝업 id 목록 추출
                    String[] popupIds = decodedString.split(",");
                    for (String popups_id : popupIds) {
                        if (!popups_id.isEmpty()) {
                            //각 팝업 id를 long 타입으로 변환하여 리스트에 추가
                            recentPopupIds.add(Long.parseLong(popups_id));
                        }
                    }
                } catch (IllegalArgumentException e) {
                    // 무시하거나 로그를 남기고 계속 진행
                    System.out.println("Invalid recentPopups format.");
                }
            }

            // 현재 팝업 ID가 리스트에 있으면 제거
            recentPopupIds.remove(popup_id);

            // 현재 팝업 ID를 목록의 맨 앞에 추가
            recentPopupIds.add(0, popup_id);

            // 리스트의 최대 크기 유지
            if (recentPopupIds.size() > 15) {
//                recentPopupIds = recentPopupIds.subList(0, 10);
//                recentPopupIds = new ArrayList<>(recentPopupIds.subList(0, 10)); // 최신 10개 유지
                recentPopupIds.subList(15, recentPopupIds.size()).clear(); // 최신 10개를 유지하도록 나머지 항목 삭제
            }

            // 최근 본 팝업 목록을 Base64로 인코딩하여 쿠키에 저장
            StringBuilder sb = new StringBuilder();
            for (Long recent_id : recentPopupIds) {
                if (sb.length() > 0) sb.append(",");
                sb.append(recent_id);
            }
            //변환된 문자열을 base64로 인코딩하여 쿠키에 저장할 수 있는 형태로 만듦
            String encodedString = Base64.getEncoder().encodeToString(sb.toString().getBytes());

            //최근 본 팝업 목록을 담은 쿠키 생성
            Cookie recentPopupsCookie = new Cookie(recentPopupsCookieName, encodedString);
            recentPopupsCookie.setMaxAge(60 * 60 * 24 * 7); // 1주일 동안 유효
            recentPopupsCookie.setPath("/");  // 모든 경로에서 접근 가능
            response.addCookie(recentPopupsCookie);
            System.out.println("최근 본 팝업 글번호 : " + recentPopupIds);
        }

        // 팝업 상세 조회
        Map<String, Object> popupMap = popupService.popupView(popup_id, id);

        // 팝업 상세 페이지로 이동
        ModelAndView mav = new ModelAndView();
        mav.setViewName("popup/popupView");
        mav.addObject("popupMap", popupMap);
        mav.addObject("loginCheck", loginCheck);
        mav.addObject("id", id);

        return mav;
    }


    @Override
    @PostMapping("/popup/popupLike.do")
    @ResponseBody
    public Map<String, Object> popupLike(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> LikeMap = new HashMap<>();
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");
        Long id = loginDTO != null ? loginDTO.getId() : null;

        if (loginDTO == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }

        boolean isLiked = popupService.toggleLike(popup_id, id);

        LikeMap.put("success", true);
        LikeMap.put("isLiked", isLiked);

        return LikeMap;
    }

    @Override
    @PostMapping("/popup/modPopupForm.do")
    public ModelAndView modPopupForm(@RequestParam("popup_id") Long popup_id, @RequestParam("user_id") Long user_id, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> popupMap = popupService.popupView(popup_id, user_id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("popupMap", popupMap);
        mav.setViewName("popup/modifyPopup");
        return mav;
    }

    @Override
    @RequestMapping("/popup/updatePopup.do")
    public ModelAndView updatePopup(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> popupMap = new HashMap<>();

        Enumeration<?> enu = multipartRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String value = multipartRequest.getParameter(name);
            popupMap.put(name, value);
        }

        String[] hashTags = multipartRequest.getParameterValues("hash_tag");
        if (hashTags != null && hashTags.length > 0) {
            popupMap.put("hash_tag", Arrays.asList(hashTags));
        }

        List<String> fileList = multiFileUpload(multipartRequest);
        String popup_id = (String) popupMap.get("popup_id");

        List<ImageDTO> imageFileList = new ArrayList<>();
        int modityNumber = 0;
        if (fileList != null && !fileList.isEmpty()) {
            for (String fileName : fileList) {
                modityNumber++;
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setImage_file_name(fileName);
                String popupImageIdStr = (String) popupMap.get("popup_image_id_" + (modityNumber - 1));
                Long popupImageId = (popupImageIdStr != null && !popupImageIdStr.isEmpty()) ? Long.parseLong(popupImageIdStr) : null;
                imageDTO.setPopup_image_id(popupImageId);
                imageFileList.add(imageDTO);
            }
            popupMap.put("imageFileList", imageFileList);
        }

        try {
            popupService.updatePopup(popupMap);

            if (imageFileList != null && !imageFileList.isEmpty()) {
                int cnt = 0;
                for (ImageDTO imageDTO : imageFileList) {

                    String imageFileName = imageDTO.getImage_file_name();
                    Long popupImageId = imageDTO.getPopup_image_id();
                    if (imageFileName != null && !imageFileName.isEmpty()) {
                        File srcFile = new File(ARTICLE_IMG_REPO + "\\temp\\" + imageFileName);
                        File destDir = new File(ARTICLE_IMG_REPO + "\\" + popup_id);
                        File destFile = new File(destDir, imageFileName);
                        File oldFile = (popupImageId != null) ? new File(ARTICLE_IMG_REPO + "\\" + popup_id + "\\" + (String) popupMap.get("image_file_name" + cnt)) : null;

                        if (oldFile != null && oldFile.exists()) {
                            if (oldFile.delete()) {
                            }
                        }
                        cnt++;

                        if (srcFile.exists()) {
                            if (!destDir.exists()) {
                                destDir.mkdirs();
                            }
                            FileUtils.moveFile(srcFile, destFile);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/popup/popupAllList.do");
    }

}