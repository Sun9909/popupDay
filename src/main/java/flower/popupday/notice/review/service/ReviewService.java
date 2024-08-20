package flower.popupday.notice.review.service;

import flower.popupday.notice.review.dto.ReviewCommentDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface ReviewService {

    int addReview(Map<String, Object> reviewMap);

    // 글목록 로그인 했을때는 map으로 확인
    //public Map popupAllList(Map<String, Integer> pageingMap) throws DataAccessException;

    // 로그인 안 했을때는 list로

    public Map reviewList(Map<String, Integer> pagingMap) throws DataAccessException;

    //후기 상세보기
    public Map viewReview(int review_id) throws DataAccessException;

    // 여러개의 이미지 글 수정
    public void modReview(Map reviewMap) throws DataAccessException;

    public void removeReviews(int review_id) throws DataAccessException;

    public void addReviewComment(ReviewCommentDTO reviewComment) throws DataAccessException;

    public void deleteComment(Long popupCommentId) throws DataAccessException;

    public void updateComment(Long reviewCommentId, Long reviewId, String content) throws DataAccessException;

    // 회원 id에 해당하는 리뷰 목록 조회
    public List<ReviewCommentDTO> selectReviewByUserId(long id);
}
