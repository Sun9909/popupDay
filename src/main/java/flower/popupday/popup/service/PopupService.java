package flower.popupday.popup.service;

import org.springframework.dao.DataAccessException;

import java.util.Map;


public interface PopupService {

    public Long addPopup(Map<String, Object> hash_tag) throws DataAccessException;

    public Map<String, Object> popupView(Long popup_id) throws  DataAccessException;

    public Map popupAllList(Map<String, Integer> pagingMap) throws  DataAccessException;

    public void updateHits(Long popup_id) throws DataAccessException;

    public boolean isLiked(Long id, Long popup_id) throws DataAccessException;

    public void removeLike(Long id, Long popup_id) throws DataAccessException;

    public void addLike(Long id, Long popup_id) throws DataAccessException;
}