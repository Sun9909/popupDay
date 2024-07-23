package flower.popupday.search.service;

import flower.popupday.search.dao.SearchDAO;
import flower.popupday.popup.dto.PopupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDAO searchDAO;

    @Override
    public List<PopupDTO> searchPopupsByHashTag(String query) throws DataAccessException {
        return searchDAO.searchPopupsByHashTag(query);
    }
}