package flower.popupday.map.service;

import flower.popupday.map.dao.MapAddressDAO;
import flower.popupday.popup.dto.PopupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapAddressServiceImpl implements MapAddressService {

    @Autowired
    private MapAddressDAO mapAddressDAO;

    @Override
    public PopupDTO addressFindById(Long popupId) {
        return mapAddressDAO.findById(popupId);
    }
}
