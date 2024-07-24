package flower.popupday.popup.dao;

import flower.popupday.popup.dto.HashTagDTO;
import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

@Mapper
public interface PopupDAO {

    public List<Map<String, Object>> selectAllPopup() throws DataAccessException;

    public List selectAllPopup(@Param("count") int count) throws DataAccessException;

    //admin
    public List pickAllPopup(@Param("count") int count) throws DataAccessException;

    public Long getNewPopupId() throws DataAccessException;

    public void insertNewPopup(Map popupMap) throws DataAccessException;

    public void insertNewImages(Map popupMap) throws DataAccessException;

    public void insertPopupHashTag(Map<String, Object> paramMap) throws DataAccessException;

    public void insertHashTag(List tagMapList) throws  DataAccessException;

    public boolean checkHashTag(String tag) throws  DataAccessException;

    public Long getHashTagIdByTag(String tag) throws  DataAccessException;

    public PopupDTO selectPopup(Long popup_id) throws DataAccessException;

    public List<ImageDTO> selectImageFileList(Long popup_id) throws DataAccessException;

    public List<HashTagDTO> selectHashTagList(Long popup_id) throws DataAccessException;

    public int selectToPopup() throws DataAccessException;

    //admin
    public int pickToPopup() throws DataAccessException;

    public ImageDTO selectFirstImage(Long popup_id) throws DataAccessException;

    public void updateHits(Long popup_id) throws DataAccessException;

    public boolean addLike(Long popup_id, Long user_id) throws DataAccessException;

    public boolean removeLike(Long popup_id, Long user_id) throws DataAccessException;

    public boolean isLiked(Long popup_id, Long user_id)throws DataAccessException;

}