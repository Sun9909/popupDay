package flower.popupday.popup.service;

import flower.popupday.popup.dao.PopupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("popupService")
public class PopupServiceImpl implements PopupService {
    @Override
    public int addPopup(Map<String, Object> articleMap) {
        return 0;
    }

    //@Autowired
    //private PopupDAO popupDAO;

}
