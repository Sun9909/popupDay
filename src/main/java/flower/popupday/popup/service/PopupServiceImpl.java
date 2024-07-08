package flower.popupday.popup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("popupService")
public class PopupServiceImpl implements PopupService {

    @Autowired
    private PopupDAO popupDAO;

}
