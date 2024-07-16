package flower.popupday.popup.service;

import flower.popupday.popup.dao.PopupDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("popupService")
public class PopupServiceImpl implements PopupService {
    private static final Logger logger = LoggerFactory.getLogger(PopupServiceImpl.class);
    @Autowired
    private PopupDAO popupDAO;

    @Transactional
    @Override
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
        List<String> hashTags = (List<String>) popupMap.get("hash_tag");
        if (hashTags != null && !hashTags.isEmpty()) {
            List<String> nonExistingHashTags = new ArrayList<>();
            for (String tag : hashTags) {
                if (!popupDAO.checkHashTagExists(tag)) {
                    nonExistingHashTags.add(tag);
                }
            }
            if (!nonExistingHashTags.isEmpty()) {
                // Map 형태로 변환하여 DAO에 전달
                List<Map<String, Object>> tagMapList = new ArrayList<>();
                for (String tag : nonExistingHashTags) {
                    Map<String, Object> tagMap = new HashMap<>();
                    tagMap.put("tag", tag);
                    tagMapList.add(tagMap);
                }
                popupDAO.insertHashTag(tagMapList);  // DAO에서는 List<Map<String, Object>>을 처리할 수 있도록 구현되어야 함
            }
            // 팝업과 해시태그 연결 정보 삽입
            for (String tag : hashTags) {
                Long hash_tag_id = popupDAO.getHashTagIdByTag(tag);
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("popup_id", popup_id);
                paramMap.put("hash_tag_id", hash_tag_id);
                popupDAO.insertPopupHashTag(paramMap);
            }
        }
        return popup_id; // 등록된 팝업 ID 반환
    }

    @Override
    public List popupList() throws DataAccessException {
        List popupList = popupDAO.selectAllPopup();
        return popupList;
    }
}