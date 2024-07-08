package flower.popupday.popup.controller;


import flower.popupday.popup.dto.PopupDTO;
import flower.popupday.popup.service.PopupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("popupController")
public class PopupControllerImpl implements PopupController{

    @Autowired
    private PopupService popupService;

    @Autowired
    private PopupDTO popupDTO;
}
