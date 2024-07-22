package flower.popupday.search.service;

import flower.popupday.search.dao.SearchDAO;
import flower.popupday.search.dto.SearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

// SearchServiceImpl 클래스 선언 및 @Service 어노테이션 추가
@Service
public class SearchServiceImpl implements SearchService {

    // SearchDAO를 자동 주입
    @Autowired
    private SearchDAO searchDAO;

    // searchHashTags 메서드 구현: query 파라미터를 받아서 DAO 메서드를 호출하여 결과를 반환
    @Override
    public List<SearchDTO> searchHashTags(String query) throws DataAccessException {
        // DAO를 사용하여 검색 결과를 가져옴
        return searchDAO.searchHashTags(query);
    }
}
