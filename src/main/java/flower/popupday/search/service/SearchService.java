package flower.popupday.search.service;

import flower.popupday.popup.dto.PopupDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

// SearchService 인터페이스 정의
public interface SearchService {

    // 해시태그로 팝업을 검색하는 메서드
    List<PopupDTO> searchPopupsByHashTag(String query);

    // 단어로 팝업을 검색하는 메서드
    List<PopupDTO> searchPopupsByWord(String query);
}