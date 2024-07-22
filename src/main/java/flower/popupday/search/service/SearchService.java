package flower.popupday.search.service;

import flower.popupday.search.dto.SearchDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

// SearchService 인터페이스 선언
public interface SearchService {
    // searchHashTags 메서드 선언: query 파라미터를 받아서 List<SearchDTO>를 반환
    List<SearchDTO> searchHashTags(String query) throws DataAccessException;
}