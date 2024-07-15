package flower.popupday.popup.service;

import flower.popupday.popup.dao.PopupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("popupService")
public class PopupServiceImpl implements PopupService {

    @Autowired
    private PopupDAO popupDAO;

    public Long addPopup(Map<String, Object> popupMap) throws DataAccessException {
        Long popup_id = popupDAO.getNewPopupId(); // 새로운 팝업 ID 가져오기
        popupMap.put("popup_id", popup_id); // Map에 팝업 ID 매핑

        // 팝업 등록
        popupDAO.insertNewPopup(popupMap);

        // 이미지 파일 등록 처리
        if (popupMap.get("imageFileList") != null) {
            popupDAO.insertNewImages(popupMap);
        }

        // 해시태그 등록 처리
        List<String> hashtags = (List<String>) popupMap.get("hashtags");
        if (hashtags != null && !hashtags.isEmpty()) {
            for (String hashtag : hashtags) {
                popupDAO.insertHashtag(popup_id, hashtag);
            }
        }

        return popup_id; // 등록된 팝업 ID 반환
    }

    @Override
    public List popupList() throws DataAccessException {
        List popupList=popupDAO.selectAllPopup();
        return popupList;
    }


}
