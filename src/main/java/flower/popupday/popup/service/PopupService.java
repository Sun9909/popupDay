package flower.popupday.popup.service;

import org.springframework.dao.DataAccessException;

import java.util.Map;


public interface PopupService {

    public Long addPopup(Map<String, Object> hash_tag) throws DataAccessException;

    // 글목록 로그인 했을때는 map으로 확인
    //public Map popupAllList(Map<String, Integer> pageingMap) throws DataAccessException;

    // 로그인 안 했을때는 list로


    public Map<String, Object> popupView(Long popup_id) throws  DataAccessException;

    public Map popupList(Map<String, Integer> pagingMap) throws  DataAccessException;
}
