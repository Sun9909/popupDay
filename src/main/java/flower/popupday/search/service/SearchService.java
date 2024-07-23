package flower.popupday.search.service;

import flower.popupday.popup.dto.PopupDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface SearchService {
    List<PopupDTO> searchPopupsByHashTag(String query) throws DataAccessException;
}