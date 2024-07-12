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

    @Override
    public int addPopup(Map popupMap) throws DataAccessException {
        int popupNo=popupDAO.getNewPopupId(); // 글번호 받아오는 메서드
        popupMap.put("articleNo",popupNo); // 얻어온 번호 주입
        popupDAO.insertNewPopup(popupMap);
        // imagefile_tbl 이용
        if(popupMap.get("imageFileList") != null) { // 이미지가 들어있을때
            popupDAO.insertNewImages(popupMap); // Map 데이터 가지고 수행
        }
        return popupNo;
    }

    @Override
    public List popupList() throws DataAccessException {
        List popupList=popupDAO.selectAllPopup();
        return popupList;
    }


}
