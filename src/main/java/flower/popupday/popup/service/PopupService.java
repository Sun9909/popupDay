package flower.popupday.popup.service;

import flower.popupday.popup.dto.PopupDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface PopupService {

    int addPopup(Map<String, Object> articleMap);

    // 글목록 로그인 했을때는 map으로 확인
    //public Map listArticles(Map<String, Integer> pageingMap) throws DataAccessException;

    // 로그인 안 했을때는 list로

    public List popupList() throws DataAccessException;

}
