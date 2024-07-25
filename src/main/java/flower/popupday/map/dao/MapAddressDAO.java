package flower.popupday.map.dao;

import flower.popupday.popup.dto.PopupDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MapAddressDAO {

    PopupDTO findById(Long popup_id);

}
