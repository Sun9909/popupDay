package flower.popupday.search.service;

import flower.popupday.search.dao.SearchDAO;
import flower.popupday.popup.dto.PopupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    // SearchDAO를 자동으로 주입
    @Autowired
    private SearchDAO searchDAO;

    // SearchService 인터페이스의 메서드를 구현
    @Override
    public List<PopupDTO> searchPopupsByHashTag(String query) throws DataAccessException {
        // searchDAO를 통해 해시태그로 팝업 검색 결과를 반환
        return searchDAO.searchPopupsByHashTag(query);
    }
}