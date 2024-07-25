package flower.popupday.map.service;

import flower.popupday.popup.dto.PopupDTO;

public interface MapAddressService {

    PopupDTO addressFindById(Long popupId);
}
