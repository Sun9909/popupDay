package flower.popupday.search.service;

import flower.popupday.popup.dto.PopupDTO;

import java.sql.Date;
import java.util.List;

public interface SearchService {
    List<PopupDTO> searchPopupHasTag(String hashTag);
    List<PopupDTO> searchPopupsByWord(String keyword);
    List<PopupDTO> searchPopupsByDate(Date selectedDate);


}
