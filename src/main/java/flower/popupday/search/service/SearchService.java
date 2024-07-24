package flower.popupday.search.service;

import flower.popupday.popup.dto.PopupDTO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface SearchService {

    List<PopupDTO> searchPopupsByHashTag(String query);

    List<PopupDTO> searchPopupsByWord(String query);

    List<PopupDTO> searchPopupsByDate(Date selectedDate);
}
