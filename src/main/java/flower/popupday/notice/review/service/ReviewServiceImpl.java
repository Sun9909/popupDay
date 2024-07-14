package flower.popupday.notice.review.service;

import flower.popupday.notice.faq.dto.FaqDTO;
import flower.popupday.notice.review.dao.ReviewDAO;
import flower.popupday.notice.review.dto.ReviewDTO;
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


    @Override
    public int addReview(Map<String, Object> reviewMap) {
        int popupNo=reviewDAO.getNewReviewId(); // 글번호 받아오는 메서드
        reviewMap.put("articleNo",popupNo); // 얻어온 번호 주입
        reviewDAO.insertNewReview(reviewMap);
        // imagefile_tbl 이용
        if(reviewMap.get("imageFileList") != null) { // 이미지가 들어있을때
            reviewDAO.insertNewImages(reviewMap); // Map 데이터 가지고 수행
        }
        return popupNo;
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
}
