package flower.popupday.search.service;

import flower.popupday.popup.dto.PopupDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface SearchService {
    // 해시태그로 팝업을 검색하는 메서드 선언, DataAccessException을 던질 수 있음
    List<PopupDTO> searchPopupsByHashTag(String query) throws DataAccessException;
}