package flower.popupday.popup.controller;


import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
import flower.popupday.popup.service.PopupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.*;


@Controller("popupController")
public class PopupControllerImpl implements PopupController {

    private static String ARTICLE_IMG_REPO="D:\\Sun\\fileupload";

    @Autowired
    PopupService popupService;

    @Autowired PopupDTO popupDTO;

//    로그인 하면 세션값으로 쓸 메서드
//    public ModelAndView listArticles(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
//    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception
    @Override
    @RequestMapping("/board/popupList.do")
    public ModelAndView popupList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        List popupList = popupService.popupList();
        mav.setViewName("/board/popupList"); // View 이름 설정
        mav.addObject("popupList", popupList); // 모델에 데이터 추가
        return mav; // ModelAndView 반환
    }

    @Override
    @RequestMapping("/popup/addPopup.do")
    public ModelAndView addPopup(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
            throws Exception {
        String image_file_name =null;
        multipartRequest.setCharacterEncoding("utf-8");

        // HTTP 요청에서 파라미터들을 Map으로 변환
        Map<String, Object> popupMap=new HashMap<String, Object>();
        Enumeration enu=multipartRequest.getParameterNames();
        while(enu.hasMoreElements()) {
            String name=(String)enu.nextElement();
            String value=multipartRequest.getParameter(name);
            popupMap.put(name, value);
        }

        // 해시태그
        String[] hashtags = multipartRequest.getParameterValues("hashtags");
        if (hashtags != null && hashtags.length > 0) {
            List<String> hashtagList = Arrays.asList(hashtags);
            popupMap.put("hashtags", hashtagList);
        }

        // 파일 업로드 처리
        List<String> fileList=multiFileUpload(multipartRequest);
        List<ImageDTO> imageFileList=new ArrayList<ImageDTO>();
        if(fileList != null && fileList.size() != 0) {
            for(String fileName : fileList) {
                ImageDTO imageDTO=new ImageDTO();
                imageDTO.setImage_file_name(fileName);
                imageFileList.add(imageDTO);
            }
            popupMap.put("imageFileList", imageFileList);
        }
        popupMap.put("id","Flower");
        try {
            // 팝업 추가 서비스 호출
            Long image_id =popupService.addPopup(popupMap);

            // 이미지 파일 이동 처리
            if(imageFileList != null && imageFileList.size() != 0) {
                for(ImageDTO imageDTO : imageFileList) {
                    image_file_name =imageDTO.getImage_file_name();
                    File srcFile=new File(ARTICLE_IMG_REPO + "\\temp\\" + image_file_name);
                    File destDir=new File(ARTICLE_IMG_REPO + "\\" + image_id);
                    FileUtils.moveFileToDirectory(srcFile, destDir, true);
                }
            }
        }catch (Exception e) {
            //글쓰기 수행 중 오류
            if(imageFileList != null && imageFileList.size() != 0) {
                for(ImageDTO imageDTO : imageFileList) {
                    image_file_name =imageDTO.getImage_file_name();
                    File srcFile=new File(ARTICLE_IMG_REPO + "\\temp\\" + image_file_name);
                    //오류 발생 시 temp폴더의 이미지를 모두 삭제
                    srcFile.delete();
                }
            }
            e.printStackTrace();

        }
        ModelAndView mav= new ModelAndView("redirect:/board/popupList.do");
        return mav;
    }
    //여러개의 이미지 파일 업로드
    private List<String> multiFileUpload(MultipartHttpServletRequest multipartRequest) throws Exception {
        List<String> fileList=new ArrayList<String>();
        Iterator<String> fileNames=multipartRequest.getFileNames();
        while(fileNames.hasNext()) {
            String fileName=fileNames.next();
            MultipartFile mFile=multipartRequest.getFile(fileName);
            String originalFileName=mFile.getOriginalFilename();
            fileList.add(originalFileName);
            File file=new File(ARTICLE_IMG_REPO+"\\"+ fileName);
            if(mFile.getSize() != 0) {
                if(! file.exists()) {
                    if(file.getParentFile().mkdirs()) {
                        file.createNewFile();
                    }
                }
                mFile.transferTo(new File(ARTICLE_IMG_REPO + "\\temp\\" + originalFileName));
            }
        }
        return fileList;
    }//method 종료

}