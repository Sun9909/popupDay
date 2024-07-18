package flower.popupday.popup.service;

import flower.popupday.popup.dao.PopupDAO;
import flower.popupday.popup.dto.HashTagDTO;
import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
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

    public Map popupView(Long popup_id) throws DataAccessException {
        Map<String, Object> popupMap = new HashMap<>();
        PopupDTO popupDTO = popupDAO.selectPopup(popup_id);
        List<ImageDTO> imageFileList = popupDAO.selectImageFileList(popup_id);
        List<HashTagDTO> hashTagList = popupDAO.selectHashTagListByPopupId(popup_id);

        popupMap.put("popup", popupDTO);
        popupMap.put("imageFileList", imageFileList);
        popupMap.put("hashTagList", hashTagList);

        return popupMap;
    }

    @Override
    public Map popupList(Map<String, Integer> pagingMap) throws DataAccessException {
        Map popupMap =new HashMap<>();
        int section=pagingMap.get("section");
        int pageNum=pagingMap.get("pageNum");
        int count=(section-1)*100+(pageNum-1)*10; // 현재 섹션에는 1
        List<PopupDTO> popupList =popupDAO.selectAllPopup(count); // boardDAO.메서드 호출 dto로 받음
        int totPopup=popupDAO.selectToPopup(); // 전체 글 목록 수 조회
        popupMap.put("popupList", popupList);
        popupMap.put("totPopup", totPopup);
//        popupMap.put("totPopup", 324); // 임시 페이지 생성
        return popupMap;
    }
}