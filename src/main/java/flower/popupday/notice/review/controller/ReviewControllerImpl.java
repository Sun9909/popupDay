package flower.popupday.notice.review.controller;

import flower.popupday.notice.faq.dto.FaqDTO;
import flower.popupday.notice.review.dto.ReviewDTO;
import flower.popupday.notice.review.dto.ReviewImageDTO;
import flower.popupday.notice.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.*;
@Controller("reviewController")
public class ReviewControllerImpl implements ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 이미지 저장 경로
    private static String ARTICLE_IMG_REPO="D:\\Sin\\fileupload2";

    //    로그인 하면 세션값으로 쓸 메서드
    //    public ModelAndView listArticles(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    //    String _pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception

    @Override
    @RequestMapping("/notice/reviewList.do")
    public ModelAndView reviewList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum,HttpServletRequest request, HttpServletResponse response) throws Exception {
        int section=Integer.parseInt((_section == null) ? "1" : _section);
        int pageNum=Integer.parseInt((_pageNum == null) ? "1" : _pageNum);
        Map<String, Integer> pagingMap=new HashMap<String, Integer>();
        pagingMap.put("section", section); // 1
        pagingMap.put("pageNum", pageNum); // 1

        Map reviewMap = reviewService.reviewList(pagingMap);
        reviewMap.put("section", section);
        reviewMap.put("pageNum", pageNum);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("notice/review"); // 여기로감
        mav.addObject("reviewMap", reviewMap); // 글목록 넘겨줌
        return mav; // 포워딩
    }

    //후기 상세보기
    @Override
    public ModelAndView showReview(@RequestParam("review_id") int review_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map reviewArticle = reviewService.showReview(review_id);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("notice/reviewShow");
        mav.addObject("reviewArticle", reviewArticle);
        return null;
    }
    
    //후기 작성페이지로 이동
    @Override
    @RequestMapping("/notice/reviewForm.do")
    public ModelAndView reviewForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("notice/reviewForm");
        return mav;
    }



    //후기 작성저장
    @Override
    @RequestMapping("/notice/addReview.do")
    public ModelAndView addReview(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
        String imageFileName=null;
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> reviewMap=new HashMap<String, Object>();
        Enumeration enu=multipartRequest.getParameterNames(); // <td> 의 name 을 가져옴 (객체취급)
        while (enu.hasMoreElements()) {
            String name=(String) enu.nextElement();
            String value=multipartRequest.getParameter(name);
            reviewMap.put(name, value); // 이미지 파일 name 까지 집어넣음
        } // while end
        List<String> fileList=multiFileUpload(multipartRequest); // 멀티파일로 가져옴
        List<ReviewImageDTO> imageFileList=new ArrayList<ReviewImageDTO>(); // 여러개의 이미지를 담음

        // 두개의 테이블을 동시에 이용
        if(fileList != null && fileList.size() != 0 ) {
            for(String fileName:fileList) { // 향상된 for fileList에 있는걸 하나씩 filename 에 넘겨줌
                ReviewImageDTO reviewImageDTO=new ReviewImageDTO(); // 이미지를 넣을때마다 생성 여러개 이미지지만 각각의 정보를 가지고 있어야함
                reviewImageDTO.setImage_file_name(fileName);
                imageFileList.add(reviewImageDTO);
            }
            reviewMap.put("imageFileList", imageFileList); // 이미지가 있을때만 put
        }
        reviewMap.put("id","Flower");
        try {
            int imageId=reviewService.addReview(reviewMap);
            if(imageFileList != null && imageFileList.size() != 0) {
                for(ReviewImageDTO reviewImageDTO : imageFileList) {
                    imageFileName=reviewImageDTO.getImage_file_name();
                    File srcFile=new File(ARTICLE_IMG_REPO + "\\temp\\" + imageFileName);
                    File destDir=new File(ARTICLE_IMG_REPO + "\\" + imageId);
                    FileUtils.moveFileToDirectory(srcFile, destDir, true);
                }
            }
        }catch (Exception e) {
            //글쓰기 수행 중 오류
            if(imageFileList != null && imageFileList.size() != 0) {
                for(ReviewImageDTO reviewImageDTO : imageFileList) {
                    imageFileName=reviewImageDTO.getImage_file_name();
                    File srcFile=new File(ARTICLE_IMG_REPO + "\\temp\\" + imageFileName);
                    //오류 발생 시 temp폴더의 이미지를 모두 삭제
                    srcFile.delete();
                }
            }
            e.printStackTrace();

        }
        ModelAndView mav= new ModelAndView("redirect:/notice/reviewList.do");
        return mav;
    }


    // 여러개의 이미지파일 업로드
    private List<String> multiFileUpload(MultipartHttpServletRequest multipartrequest) throws Exception{
        List<String> fileList=new ArrayList<String>();
        Iterator<String> fileNames=multipartrequest.getFileNames(); // 열거형 객체(여러개)
        while(fileNames.hasNext()) { // has.Next 파일 이름이 없을때 까지 돔
            String fileName=fileNames.next(); // 첨부한 이미지 파일 이름
            MultipartFile mFile=multipartrequest.getFile(fileName); // 파일 크기
            String originalFileName=mFile.getOriginalFilename(); //  파일 name 얻어오기
            fileList.add(originalFileName); // 파일 이름 얻어온걸 하나씩 저장
            File file=new File(ARTICLE_IMG_REPO + "\\" + fileName); // 경로 저장
            if(mFile.getSize() != 0) { // 크기가 0인 이미지 거르기
                if(! file.exists()) { // exists 존재하는지(not이라 존재 안할때) , ex ) 기존에 있던 이미지를 또 추가하면 안됨
                    if(file.getParentFile().mkdir()) { // mkdir 폴더 생성
                        file.createNewFile();
                    } // inner if end
                } // inner if end
                mFile.transferTo(new File(ARTICLE_IMG_REPO + "\\temp\\" + originalFileName)); // 파일 전달 (임시저장소에)
            } // if end
        } // while end
        return fileList;
    }
}
