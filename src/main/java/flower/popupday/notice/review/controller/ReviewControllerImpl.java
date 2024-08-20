package flower.popupday.notice.review.controller;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.notice.review.dto.ReviewCommentDTO;
import flower.popupday.notice.review.dto.ReviewImageDTO;
import flower.popupday.notice.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.*;
@Controller("reviewController")
public class ReviewControllerImpl implements ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 이미지 저장 경로
    private static String ARTICLE_IMG_REPO="D:\\Sin\\fileupload2";

    @Override
    @RequestMapping("/notice/reviewList.do")
    public ModelAndView reviewList(@RequestParam(value = "section", required = false) String _section, @RequestParam(value = "pageNum", required = false)
    String _pageNum,HttpServletRequest request, HttpServletResponse response) throws Exception {
        int section=Integer.parseInt((_section == null) ? "1" : _section);
        int pageNum=Integer.parseInt((_pageNum == null) ? "1" : _pageNum);
        Map<String, Integer> pagingMap=new HashMap<String, Integer>();
        pagingMap.put("section", section);
        pagingMap.put("pageNum", pageNum);

        Map<String, Integer> reviewMap = reviewService.reviewList(pagingMap);
        reviewMap.put("section", section);
        reviewMap.put("pageNum", pageNum);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("notice/reviewList");
        mav.addObject("reviewMap", reviewMap);
        return mav;
    }

    //후기 상세보기
    @Override
    @RequestMapping("/notice/viewReview.do")
    public ModelAndView viewReview(@RequestParam("review_id") int review_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        LoginDTO loginDTO = (LoginDTO) session.getAttribute("loginDTO");
        Map reviewArticle = reviewService.viewReview(review_id);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("notice/reviewView");
        mav.addObject("reviewArticle", reviewArticle);
        mav.addObject("loginDTO", loginDTO);
        return mav;
    }

    //후기 수정적용하기
    @Override
    @RequestMapping("/notice/modReview.do")
    public ModelAndView modReview(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
        String imageFileName=null;
        multipartRequest.setCharacterEncoding("utf-8");
        Map<String, Object> reviewMap=new HashMap<String, Object>();
        Enumeration enu=multipartRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String name=(String) enu.nextElement();
            String value=multipartRequest.getParameter(name);
            reviewMap.put(name, value);
        } // while end
        List<String> fileList=multiFileUpload(multipartRequest);
        String articleNo=(String)reviewMap.get("review_id");
        List<ReviewImageDTO> imageFileList=new ArrayList<ReviewImageDTO>();
        int modityNumber=0;
        if(fileList != null && fileList.size() != 0 ) {
            for(String fileName:fileList) {
                modityNumber++;
                ReviewImageDTO imageDTO=new ReviewImageDTO();
                imageDTO.setImage_file_name(fileName);
                imageDTO.setReview_image_id(Integer.parseInt((String) reviewMap.get("review_image_id" + modityNumber)));
                imageFileList.add(imageDTO);
            }
            reviewMap.put("imageFileList", imageFileList);
        }
        HttpSession session=multipartRequest.getSession();
        LoginDTO loginDTO=(LoginDTO)session.getAttribute("loginDTO");
        Long id=loginDTO.getId();
        reviewMap.put("id", id);
        try {
            reviewService.modReview(reviewMap);
            if(imageFileList != null && imageFileList.size() != 0) {

                int cnt=0;
                for(ReviewImageDTO imageDTO : imageFileList) {
                    cnt++;
                    imageFileName=imageDTO.getImage_file_name();
                    if(imageFileName != null && imageFileName != "") {
                        File srcFile=new File(ARTICLE_IMG_REPO + "\\temp\\" + imageFileName);
                        File destDir=new File(ARTICLE_IMG_REPO + "\\" + articleNo);
                        FileUtils.moveFileToDirectory(srcFile, destDir, true);
                        String originalFileName=(String)reviewMap.get("originalFileName" + cnt);
                        System.out.println("이전 이미지 " + originalFileName);
                        File oldFile=new File(ARTICLE_IMG_REPO + "\\" + articleNo + "\\" + originalFileName);
                        oldFile.delete();
                    }
                } // for end
            } // if end
        }catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView mav = new ModelAndView("redirect:/notice/reviewList.do");
        return mav;
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
        Enumeration enu=multipartRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String name=(String) enu.nextElement();
            String value=multipartRequest.getParameter(name);
            reviewMap.put(name, value);
        } // while end
        List<String> fileList=multiFileUpload(multipartRequest);
        List<ReviewImageDTO> imageFileList=new ArrayList<ReviewImageDTO>();

        if(fileList != null && fileList.size() != 0 ) {
            for(String fileName:fileList) {
                ReviewImageDTO reviewImageDTO=new ReviewImageDTO();
                reviewImageDTO.setImage_file_name(fileName);
                imageFileList.add(reviewImageDTO);
            }
            reviewMap.put("imageFileList", imageFileList);
        }
        HttpSession session=multipartRequest.getSession();
        LoginDTO loginDTO=(LoginDTO)session.getAttribute("loginDTO");
        Long id=loginDTO.getId();
        Long tot_point = loginDTO.getTot_point();
        reviewMap.put("id", id);
        String name = loginDTO.getName();
        reviewMap.put("name", name);
        reviewMap.put("tot_point", tot_point);
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
        Iterator<String> fileNames=multipartrequest.getFileNames();
        while(fileNames.hasNext()) {
            String fileName=fileNames.next();
            MultipartFile mFile=multipartrequest.getFile(fileName);
            String originalFileName=mFile.getOriginalFilename();
            fileList.add(originalFileName);
            File file=new File(ARTICLE_IMG_REPO + "\\" + fileName);
            if(mFile.getSize() != 0) {
                if(! file.exists()) {
                    if(file.getParentFile().mkdir()) {
                        file.createNewFile();
                    }
                }
                mFile.transferTo(new File(ARTICLE_IMG_REPO + "\\temp\\" + originalFileName));
            } // if end
        } // while end
        return fileList;
    }

    @Override
    @RequestMapping("/notice/removeReview.do")
    public ModelAndView removeReview(@RequestParam("review_id") int review_id, HttpServletRequest request, HttpServletResponse response)
    throws Exception{
        reviewService.removeReviews(review_id);
        File imgDir=new File(ARTICLE_IMG_REPO + "\\" + review_id); // 파일 객체로 만듬
        if(imgDir.exists()) {
            FileUtils.deleteDirectory(imgDir);
        }
        ModelAndView mav=new ModelAndView("redirect:/notice/reviewList.do"); // 글 삭제 후 redirect 로 글목록 포워딩
        return mav;
    }

    @Override
    @PostMapping("/reviewComment/addReviewComment.do")
    public ModelAndView addReviewComment(@RequestParam("review_id") Long review_id,
                                         @RequestParam("user_id") Long user_id,
                                         @RequestParam("content") String content,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {

        // ReviewComment 객체를 생성하고 데이터 설정
        ReviewCommentDTO reviewComment = new ReviewCommentDTO();
        reviewComment.setReview_id(review_id);
        reviewComment.setUser_id(user_id);
        reviewComment.setContent(content);

        // 댓글 등록 서비스 호출
        reviewService.addReviewComment(reviewComment);

        // 리뷰 상세보기 페이지로 리다이렉트
        ModelAndView mav = new ModelAndView("redirect:/notice/viewReview.do?review_id=" + review_id);
        return mav;
    }

    @Override
    @PostMapping("reviewComment/deleteReviewComment.do")
    public String deleteComment(@RequestParam(value = "review_id", required = true) Long reviewId,
                                @RequestParam("review_comment_id") Long reviewCommentId,
                                RedirectAttributes redirectAttributes) {
        try {
            reviewService.deleteComment(reviewCommentId);
            redirectAttributes.addFlashAttribute("message", "리뷰가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "리뷰 삭제에 실패했습니다.");
        }
        return "redirect:/notice/viewReview.do?review_id=" + reviewId; // 삭제 후 리다이렉트할 페이지로 변경
    }
}
