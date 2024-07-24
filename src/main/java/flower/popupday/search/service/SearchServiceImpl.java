package flower.popupday.search.service;

import flower.popupday.popup.dto.PopupDTO;
import flower.popupday.search.dao.SearchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
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

    @Override
    public List<PopupDTO> searchPopupsByDate(Date selectedDate) {
        return searchDAO.searchPopupsByDate(selectedDate);
    }
}
