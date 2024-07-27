package flower.popupday.search.service;

import flower.popupday.popup.dto.PopupDTO;
import flower.popupday.search.dao.SearchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDAO searchDAO;

    @Override
    public List<PopupDTO> searchPopupHasTag(String hashTag) {
        return searchDAO.searchPopupHasTag(hashTag);
    }

    @Override
    public List<PopupDTO> searchPopupsByWord(String keyword) {
        return searchDAO.searchPopupsByWord(keyword);
    }

    @Override
    public List<PopupDTO> searchPopupsByDate(Date selectedDate) {
        return searchDAO.searchPopupsByDate(selectedDate);
    }


}
