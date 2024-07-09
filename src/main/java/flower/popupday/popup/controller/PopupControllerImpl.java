package flower.popupday.popup.controller;


import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
import flower.popupday.popup.service.PopupService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.*;


@Controller("popupController")
public class PopupControllerImpl implements PopupController {

    private static String ARTICLE_IMG_REPO="D:\\choi_teacher\\fileupload";

    @Autowired
    PopupService popupService;

    @Autowired PopupDTO popupDTO;

    @Override
    @RequestMapping("/popup/addPopup.do")
    public ModelAndView addArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
            throws Exception {
        String imageFileName=null;
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> articleMap=new HashMap<String, Object>();
        Enumeration enu=multipartRequest.getParameterNames();
        while(enu.hasMoreElements()) {
            String name=(String)enu.nextElement();
            String value=multipartRequest.getParameter(name);
            articleMap.put(name, value);
        }
        List<String> fileList=multiFileUpload(multipartRequest);
        List<ImageDTO> imageFileList=new ArrayList<ImageDTO>();
        if(fileList != null && fileList.size() != 0) {
            for(String fileName : fileList) {
                ImageDTO imageDTO=new ImageDTO();
                imageDTO.setImage_file_name(fileName);
                imageFileList.add(imageDTO);
            }
            articleMap.put("imageFileList", imageFileList);
        }
        articleMap.put("id","chulsu");
        try {
            int imageId=popupService.addPopup(articleMap);
            if(imageFileList != null && imageFileList.size() != 0) {
                for(ImageDTO imageDTO : imageFileList) {
                    imageFileName=imageDTO.getImage_file_name();
                    File srcFile=new File(ARTICLE_IMG_REPO + "\\temp\\" + imageFileName);
                    File destDir=new File(ARTICLE_IMG_REPO + "\\" + imageId);
                    FileUtils.moveFileToDirectory(srcFile, destDir, true);
                }
            }
        }catch (Exception e) {
            //글쓰기 수행 중 오류
            if(imageFileList != null && imageFileList.size() != 0) {
                for(ImageDTO imageDTO : imageFileList) {
                    imageFileName=imageDTO.getImage_file_name();
                    File srcFile=new File(ARTICLE_IMG_REPO + "\\temp\\" + imageFileName);
                    //오류 발생 시 temp폴더의 이미지를 모두 삭제
                    srcFile.delete();
                }
            }
        }
        ModelAndView mav= new ModelAndView("redirect:/board/listArticles.do");
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
                    if(file.getParentFile().mkdir()) {
                        file.createNewFile();
                    }
                }
                mFile.transferTo(new File(ARTICLE_IMG_REPO + "\\temp\\" + originalFileName));
            }
        }
        return fileList;
    }//method 종료

}