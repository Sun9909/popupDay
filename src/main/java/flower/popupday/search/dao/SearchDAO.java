package flower.popupday.search.dao;

import flower.popupday.popup.dto.PopupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface SearchDAO {
    List<PopupDTO> searchPopupsByHashTag(String query) throws DataAccessException;
}
