package flower.popupday.popup.service;

import flower.popupday.popup.dto.PopupDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;


public interface PopupService {

    public Long addPopup(Map<String, Object> hash_tag) throws DataAccessException;

    public Map<String, Object> popupView(Long popup_id, Long id) throws  DataAccessException;

    public Map selectPopupList(Map<String, Object> pagingMap) throws  DataAccessException;

    public void updateHits(Long popup_id) throws DataAccessException;

    public boolean toggleLike(Long popup_id, Long id) throws DataAccessException;

    public void updatePopup(Map<String, Object> popupMap) throws DataAccessException;

    public Map<String, Object> mainView(Long id) throws DataAccessException;

    public List<PopupDTO> searchPopupHasTag(String hashTag) throws DataAccessException;


}