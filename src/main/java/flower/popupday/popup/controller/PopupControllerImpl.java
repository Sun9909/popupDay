package flower.popupday.popup.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
import flower.popupday.popup.service.PopupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.*;

@Controller("popupController")
public class PopupControllerImpl implements PopupController {

    private static String ARTICLE_IMG_REPO = "D:\\Sun\\fileupload";

    @Autowired
    PopupService popupService;

//    @Override
//    @RequestMapping("/board/popupAllList.do") // 글 목록 섹션 페이지넘에 값이없을때 초기값 null
//    public ModelAndView popupAllList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
//    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        int section = Integer.parseInt((_section == null) ? "1" : _section);
//        int pageNum = Integer.parseInt((_pageNum == null) ? "1" : _pageNum);
//        Map<String, Integer> pagingMap = new HashMap<>();
//        pagingMap.put("section", section); // 1
//        pagingMap.put("pageNum", pageNum); // 1
//        Map popupMap = popupService.popupAllList(pagingMap); // 서비스에서 글목록 받아오기
//        popupMap.put("section", section);
//        popupMap.put("pageNum", pageNum);
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("/board/popupAllList"); // 여기로감
//        mav.addObject("popupMap", popupMap); // 글목록 넘겨줌
//        return mav; // 포워딩
//    }

    @Override
    @RequestMapping("/board/popupAllList.do")
    public ModelAndView popupAllList(@RequestParam(value = "section", required = false) String _section,
                                     @RequestParam(value = "pageNum", required = false) String _pageNum,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        int section = Integer.parseInt((_section == null) ? "1" : _section);
        int pageNum = Integer.parseInt((_pageNum == null) ? "1" : _pageNum);
        Map<String, Integer> pagingMap = new HashMap<>();
        pagingMap.put("section", section); // 섹션
        pagingMap.put("pageNum", pageNum); // 페이지 번호
        Map<String, Object> popupMap = popupService.popupList(pagingMap); // 서비스에서 팝업 목록 받아오기

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/board/popupAllList"); // View 이름 설정
        mav.addObject("popupInfoList", popupMap.get("popupInfoList")); // 팝업 정보 리스트를 View로 전달
        mav.addObject("totPopup", popupMap.get("totPopup")); // 전체 팝업 수를 View로 전달
        mav.addObject("section", section);
        mav.addObject("pageNum", pageNum);

        return mav; // ModelAndView 반환
    }



    @Override
    @GetMapping("/board/popupForm.do")
    public ModelAndView popupForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/board/popupForm");
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
        HttpSession session=multipartRequest.getSession();
        LoginDTO loginDTO =(LoginDTO)session.getAttribute("member");
        Long id= loginDTO.getId();
        popupMap.put("id", id); // 세션을 이용해 로그인한 아이디 집어넣으면 됨

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

        return new ModelAndView("redirect:/board/popupAllList.do");
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

    @Override
    @GetMapping("/board/popupView.do")
    public ModelAndView popupView(@RequestParam("popup_id") Long popup_id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> popupMap = popupService.popupView(popup_id);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/board/popupView");
        mav.addObject("popupMap", popupMap);
        return mav;
    }
}