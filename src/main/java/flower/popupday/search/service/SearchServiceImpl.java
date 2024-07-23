package flower.popupday.search.service;

import flower.popupday.search.dao.SearchDAO;
import flower.popupday.popup.dto.PopupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // 이 클래스가 서비스 계층의 컴포넌트임을 나타냅니다.
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDAO searchDAO;

    @Override
    public List<PopupDTO> searchPopupsByHashTag(String query) {
        return searchDAO.searchPopupsByHashTag(query);
    }

    @Override
    public List<PopupDTO> searchPopupsByWord(String query) {
        return searchDAO.searchPopupsByWord(query);
    }
}