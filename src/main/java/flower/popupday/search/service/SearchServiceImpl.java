package flower.popupday.search.service;

import flower.popupday.popup.dto.PopupDTO;
import flower.popupday.search.dao.SearchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    @Override
    public List<PopupDTO> searchFinishedPopups(Date today) {
        return searchDAO.searchFinishedPopups(today);
    }

    @Override
    public List<PopupDTO> searchOngoingPopups(Date today) {
        return searchDAO.searchOngoingPopups(today);
    }

    @Override
    public List<PopupDTO> searchUpcomingPopups(Date today) {
        return searchDAO.searchUpcomingPopups(today);
    }

}
