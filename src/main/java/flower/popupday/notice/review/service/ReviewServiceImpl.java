package flower.popupday.notice.review.service;

import flower.popupday.notice.review.dao.ReviewDAO;
import flower.popupday.notice.review.dto.ReviewDTO;
import flower.popupday.notice.review.dto.ReviewImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;

    //후기추가
    @Override
    public int addReview(Map<String, Object> reviewMap) {
        int review_id=reviewDAO.getNewReviewId(); // 글번호 받아오는 메서드
        reviewMap.put("review_id",review_id); // 얻어온 번호 주입
        reviewDAO.insertNewReview(reviewMap);
        // imagefile_tbl 이용
        if(reviewMap.get("imageFileList") != null) { // 이미지가 들어있을때
            reviewDAO.insertNewImages(reviewMap); // Map 데이터 가지고 수행
        }
        return review_id;
    }

    @Override
    public Map reviewList(Map<String, Integer> pagingMap) throws DataAccessException {
        Map listMap=new HashMap<>();
        int section=pagingMap.get("section");
        int pageNum=pagingMap.get("pageNum");
        int count=(section-1)*100+(pageNum-1)*10;

        List<ReviewDTO> reviewList=reviewDAO.selectAllReview(count);
        int totReview=reviewDAO.selectToReview();
        listMap.put("reviewList",reviewList);
        listMap.put("totReview",totReview);

        return listMap;
    }

    //후기 상세보기
    @Override
    public Map showReview(int review_id) throws DataAccessException {
        Map listMap=new HashMap<>();
        ReviewDTO reviewDTO = reviewDAO.selectReview(review_id); // 선택한 글번호의 정보 가져오기
        List<ReviewImageDTO> imageFiles = reviewDAO.selectImageList(review_id); //선택한 글번호의 이미지
        listMap.put("imageFiles",imageFiles);
        listMap.put("review",reviewDTO);
        return listMap;
    }

    //후기 수정반영하기
    @Override
    public void modReview(Map reviewMap) throws DataAccessException {
        reviewDAO.updateReview(reviewMap);
        reviewDAO.updateImage(reviewMap);
    }
    
    //후기 삭제하기
    @Override
    public void removeReviews(int review_id) throws DataAccessException {
        reviewDAO.deleteImage(review_id);
        reviewDAO.deleteReview(review_id);
    }


}
