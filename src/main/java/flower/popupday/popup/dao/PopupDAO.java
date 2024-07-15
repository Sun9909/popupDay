package flower.popupday.popup.dao;

import flower.popupday.popup.dto.PopupHashTagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

@Mapper
public interface PopupDAO {

    public List selectAllPopup() throws DataAccessException;

    public int getNewPopupId() throws DataAccessException;

    public void insertNewPopup(Map popupMap) throws DataAccessException;

    public void insertNewImages(Map popupMap) throws DataAccessException;

//    public void insertHashtag(int popup_id, String hash_tag) throws DataAccessException;

    public void insertPopupHashTag(Map popupMap) throws DataAccessException;

    public void insertHashtag(int popup_id,String hashtag) throws DataAccessException;

}
