package flower.popupday.popup.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface PopupDAO {

    public List selectAllPopup() throws DataAccessException;

    public int getNewPopupId() throws DataAccessException;

    public void insertNewPopup(Map articleMap) throws DataAccessException;

    public void insertNewImages(Map articleMap) throws DataAccessException;
}
