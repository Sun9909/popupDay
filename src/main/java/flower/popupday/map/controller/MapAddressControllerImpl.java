package flower.popupday.map.controller;

import flower.popupday.map.service.MapAddressService;
import flower.popupday.popup.dto.PopupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class MapAddressControllerImpl implements MapAddressController {

    @Autowired
    private MapAddressService mapAddressService;

    @Override
    @GetMapping("/popupDetail")
    public PopupDTO getPopupDetail(@RequestParam("popupId") Long popupId) {
        return mapAddressService.addressFindById(popupId);
    }
}
