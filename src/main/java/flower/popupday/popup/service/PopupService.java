package flower.popupday.popup.service;

import org.springframework.dao.DataAccessException;

import javax.swing.*;
import java.util.List;
import java.util.Map;


public interface PopupService {

    public Long addPopup(Map<String, Object> hash_tag) throws DataAccessException;

    public Map<String, Object> popupView(Long popup_id, Long id) throws  DataAccessException;

    public Map popupAllList(Map<String, Integer> pagingMap) throws  DataAccessException;

    //admin
    public Map<String, Object> registerList(Map<String, Integer> pagingMap) throws  DataAccessException;

    public Map<String, Object> register(Long popup_id) throws DataAccessException;

    public void roleUpdate(Long popup_id, String role) throws DataAccessException;

    public void updateHits(Long popup_id) throws DataAccessException;

    public boolean toggleLike(Long popup_id, Long id) throws DataAccessException;

    public void updatePopup(Map popupMap) throws DataAccessException;

    public List<Popup> topViewPopup() throws DataAccessException;
}