package flower.popupday.notice.review.service;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface ReviewService {

    int addReview(Map<String, Object> reviewMap);

    // 글목록 로그인 했을때는 map으로 확인
    //public Map listArticles(Map<String, Integer> pageingMap) throws DataAccessException;

    // 로그인 안 했을때는 list로

    public Map reviewList(Map<String, Integer> pagingMap) throws DataAccessException;
}
