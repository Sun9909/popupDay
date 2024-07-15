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

    public int addPopup(Map<String, Object> popupMap) throws DataAccessException {
        int popup_id = popupDAO.getNewPopupId(); // 새로운 팝업 ID 가져오기
        popupMap.put("popup_id", popup_id); // Map에 팝업 ID 매핑

        // 팝업 등록
        popupDAO.insertNewPopup(popupMap);

        // 해시태그 등록
        List<String> hashTags = (List<String>) popupMap.get("hashtags");
        if (hashTags != null && !hashTags.isEmpty()) {
            for (String hashtag : hashTags) {
                popupDAO.insertHashtag(popup_id, hashtag); // DAO의 insertHashtag 메서드를 호출하여 해시태그 등록
            }
        }

        // 이미지 파일 등록 처리
        if (popupMap.get("imageFileList") != null) {
            popupDAO.insertNewImages(popupMap);
        }

        return popup_id; // 등록된 팝업 ID 반환
    }

    @Override
    public List popupList() throws DataAccessException {
        List popupList = popupDAO.selectAllPopup();
        return popupList;
    }
}
