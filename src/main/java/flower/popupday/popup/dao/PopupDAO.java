package flower.popupday.popup.dao;

import flower.popupday.popup.dto.HashTagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

@Mapper
public interface PopupDAO {

    public List selectAllPopup() throws DataAccessException;

    public Long getNewPopupId() throws DataAccessException;

    public void insertNewPopup(Map popupMap) throws DataAccessException;

    public void insertNewImages(Map popupMap) throws DataAccessException;

//    public void insertHashtag(int popup_id, String hash_tag) throws DataAccessException;

//    public void insertPopupHashTag(Map popupMap) throws DataAccessException;

    public void insertHashTag(HashTagDTO hashTagDTO) throws  DataAccessException;

    public Long getNewHashTagId() throws DataAccessException;

    //public void insertHashTag(List<String> hashTags) throws DataAccessException;

    public void insertPopupHashTag(Map<String, Object> paramMap) throws DataAccessException;

    public void insertHashTag(List tagMapList) throws  DataAccessException;

    public boolean checkHashTagExists(String tag) throws  DataAccessException;

    public Long getHashTagIdByTag(String tag) throws  DataAccessException;

    public void insertHashTag(String tag) throws  DataAccessException;
}
