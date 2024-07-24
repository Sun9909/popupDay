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
                if (!popupDAO.checkHashTag(tag)) {
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


    // 팝업 상세보기
    public Map<String, Object>  popupView(Long popup_id, Long id) throws DataAccessException {
        Map<String, Object> popupMap = new HashMap<>();
        PopupDTO popupList = popupDAO.selectPopup(popup_id);
        List<ImageDTO> imageFileList = popupDAO.selectImageFileList(popup_id);
        List<HashTagDTO> hashTagList = popupDAO.selectHashTagList(popup_id);

        // 찜 상태 조회
        boolean isLiked = id != null && popupDAO.isLiked(popup_id, id);

        popupMap.put("isLiked", isLiked);
        popupMap.put("popupList", popupList);
        popupMap.put("imageFileList", imageFileList);
        popupMap.put("hashTagList", hashTagList);

        return popupMap;
    }

    // 팝업 전체 리스트
    @Override
    public Map<String, Object> popupAllList(Map<String, Integer> pagingMap) throws DataAccessException {
        Map<String, Object> popupMap = new HashMap<>();
        int section = pagingMap.get("section");
        int pageNum = pagingMap.get("pageNum");
        int count = (section - 1) * 100 + (pageNum - 1) * 10; // 현재 섹션에는 1
        List<PopupDTO> popupList = popupDAO.selectAllPopup(count); // 팝업 목록 조회
        int totPopup = popupDAO.selectToPopup(); // 전체 팝업 수 조회

        List<Map<String, Object>> popupInfoList = new ArrayList<>();
        for (PopupDTO popup : popupList) {
            Long popup_id = popup.getPopup_id();
            ImageDTO thumbnailImage = popupDAO.selectFirstImage(popup_id); // 각 팝업의 첫 번째 이미지 조회
            Map<String, Object> popupInfo = new HashMap<>();
            popupInfo.put("popup", popup); // 팝업 정보 추가
            popupInfo.put("thumbnailImage", thumbnailImage); // 이미지 정보 추가
            popupInfoList.add(popupInfo);
        }

        popupMap.put("popupInfoList", popupInfoList); // 팝업 정보 리스트 추가
        popupMap.put("totPopup", totPopup);
        return popupMap;
    }

    @Override
    public Map<String, Object> registerList(Map<String, Integer> pagingMap) throws DataAccessException {
        Map<String, Object> popupListMap = new HashMap<>();
        int section = pagingMap.get("section");
        int pageNum = pagingMap.get("pageNum");
        int count = (section - 1) * 100 + (pageNum - 1) * 10; // 현재 섹션에는 1
        List<PopupDTO> popupList = popupDAO.pickAllPopup(count); // 팝업 목록 조회
        int totPopup = popupDAO.pickToPopup(); // 전체 팝업 수 조회

        List<Map<String, Object>> popupInfoList = new ArrayList<>();
        for (PopupDTO popup : popupList) {
            Long popup_id = popup.getPopup_id();
            ImageDTO thumbnailImage = popupDAO.selectFirstImage(popup_id); // 각 팝업의 첫 번째 이미지 조회
            Map<String, Object> popupInfo = new HashMap<>();
            popupInfo.put("popup", popup); // 팝업 정보 추가
            popupInfo.put("thumbnailImage", thumbnailImage); // 이미지 정보 추가
            popupInfoList.add(popupInfo);
        }

        popupListMap.put("popupInfoList", popupInfoList); // 팝업 정보 리스트 추가
        popupListMap.put("totPopup", totPopup);
        return popupListMap;
    }

    // 조회수 증가
    @Override
    public void updateHits(Long popup_id) throws DataAccessException {
        // 조회수 증가를 위한 메서드 호출
        popupDAO.updateHits(popup_id);
    }

    @Override
    public boolean toggleLike(Long popup_id, Long id) {
        // 기존 찜 여부 확인
        boolean isLiked = popupDAO.isLiked(popup_id, id);

        if (isLiked) {
            // 찜이 이미 있는 경우, 제거
            popupDAO.removeLike(popup_id, id);
            return false; // 찜이 해제됨
        } else {
            // 찜이 없는 경우, 추가
            popupDAO.addLike(popup_id, id);
            return true; // 찜이 추가됨
        }
    }
}