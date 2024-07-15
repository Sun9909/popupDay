package flower.popupday.popup.service;

import flower.popupday.popup.dao.PopupDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        logger.info("해시태그 리스트: {}", hashTags);

        if (hashTags != null && !hashTags.isEmpty()) {
            popupDAO.insertHashTag(hashTags);
        }

//        List<String> hashTags = (List<String>) popupMap.get("hash_tag");
//        if (hashTags != null && !hashTags.isEmpty()) {
//            for (String tag : hashTags) {
//                HashTagDTO hashTagDTO = new HashTagDTO();
//                //hashTagDTO.setHash_tag_id(popup_id);
//                hashTagDTO.setHash_tag(tag);
//                popupDAO.insertHashTag(hashTagDTO);
//
//            }
//        }

        return popup_id; // 등록된 팝업 ID 반환
    }

    @Override
    public List popupList() throws DataAccessException {
        List popupList = popupDAO.selectAllPopup();
        return popupList;
    }
}
