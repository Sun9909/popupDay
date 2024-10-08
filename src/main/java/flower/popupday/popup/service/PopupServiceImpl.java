package flower.popupday.popup.service;

import flower.popupday.popup.dao.PopupDAO;
import flower.popupday.popup.dto.HashTagDTO;
import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("popupService")
public class PopupServiceImpl implements PopupService {
    private static final Logger logger = LoggerFactory.getLogger(PopupServiceImpl.class);

    @Autowired
    private PopupDAO popupDAO;

//    @Override
//    public Map <String, Object> mainView(HttpSession session) throws DataAccessException {
//        Map<String, Object> mainMap = new HashMap<>();
//        List<PopupDTO> bestPopupList = popupDAO.bestPopup();
//        List<HashTagDTO> bestHashTagList = popupDAO.bestHashTagList();
//        mainMap.put("bestPopupList",bestPopupList);
//        mainMap.put("bestHashTagList",bestHashTagList);
//        return mainMap;
//    }

    @Override
    public Map<String, Object> mainView(Long id) throws DataAccessException {
        Map<String, Object> mainMap = new HashMap<>();

        // 로그인 상태 및 사용자 ID 확인
        boolean isLoggedIn = id != null;
//        Long id = null;
        boolean userSelectedHashtags = false;

        if (isLoggedIn) {
            // 세션에서 사용자 ID를 직접 가져옴
//            id = (Long) session.getAttribute("id");
            //System.out.println("id: " + id);


            // 사용자 해시태그 선택 여부 확인
            userSelectedHashtags = popupDAO.hasSelectedHashtags(id);
            //System.out.println("userSelectedHashtags: " + userSelectedHashtags);

        }

        // 팝업 리스트 조회
        List<PopupDTO> bestPopupList = popupDAO.bestPopup();

        // 해시태그 리스트 조회 (로그인 상태 및 해시태그 선택 여부에 따라)
        List<HashTagDTO> bestHashTagList = popupDAO.bestHashTagList(isLoggedIn, userSelectedHashtags, id);

        System.out.println("bestHashTagList size: " + (bestHashTagList != null ? bestHashTagList.size() : 0));

        // 결과를 Map에 저장
        mainMap.put("bestPopupList", bestPopupList);
        mainMap.put("bestHashTagList", bestHashTagList);

        return mainMap;
    }


    @Override
    public List<PopupDTO> searchPopupHasTag(String hashtag) {
        return popupDAO.searchPopupHasTag(hashtag);
    }

    @Override
    public Map<String, Object> selectPopupList(Map<String, Object> filterParams) {
        String filter = (String) filterParams.get("filter");
        int pageNum = (int) filterParams.get("pageNum");
        int section = (int) filterParams.get("section");
        int itemsPerPage = 9;
        int count = (section - 1) * 90 + (pageNum - 1) * 9;
        int totalPopups;

        List<PopupDTO> popupList;

        switch (filter) {
            case "ongoing":
                popupList = popupDAO.selectOngoingPopup(count);
                break;
            case "upcoming":
                popupList = popupDAO.selectUpcomingPopup(count);
                break;
            case "ended":
                popupList = popupDAO.selectEndPopup(count);
                break;
            case "all":
            default:
                popupList = popupDAO.selectAllPopup(count);
                break;
        }
        totalPopups = popupList.size(); // 한 번의 쿼리에서 가져온 결과의 총 개수를 사용

        List<Map<String, Object>> popupInfoList = new ArrayList<>();
        for (PopupDTO popup : popupList) {
            Long popup_id = popup.getPopup_id();
            ImageDTO thumbnailImage = popupDAO.selectFirstImage(popup_id);
            Map<String, Object> popupInfo = new HashMap<>();
            popupInfo.put("popup", popup);
            popupInfo.put("thumbnailImage", thumbnailImage);
            popupInfoList.add(popupInfo);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("popupInfoList", popupInfoList);
        response.put("totPopup", totalPopups);

        return response;
    }

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

    public void updatePopup(Map<String, Object> popupMap) {
        // 글 수정
        popupDAO.updatePopup(popupMap);

        // popup_id를 String으로 가져와 Long으로 변환
        Long popup_id;
        Object popupIdObj = popupMap.get("popup_id");
        if (popupIdObj instanceof String) {
            popup_id = Long.parseLong((String) popupIdObj);
        } else if (popupIdObj instanceof Long) {
            popup_id = (Long) popupIdObj;
        } else {
            throw new IllegalArgumentException("Invalid popup_id type");
        }

        // 만약 popupMap에 popup_id가 없으면 새로 가져옴
        if (popup_id == null) {
            popup_id = popupDAO.getNewPopupId();
            popupMap.put("popup_id", popup_id);
        }

        // 이미지 수정
        popupDAO.updateImage(popupMap);

        // 기존 해시태그 삭제
        popupDAO.deletePopupHashTag(popup_id);

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
    }


}

