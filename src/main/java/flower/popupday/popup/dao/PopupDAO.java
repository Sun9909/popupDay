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

    public List selectAllPopup(@Param("count") int count) throws DataAccessException;

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

    public ImageDTO selectFirstImage(Long popup_id) throws DataAccessException;

    public HashTagDTO selectHashTagList2(Long popup_id) throws DataAccessException;

    public void updateHits(Long popup_id) throws DataAccessException;

    public boolean addLike(Long popup_id, Long user_id) throws DataAccessException;

    public boolean removeLike(Long popup_id, Long user_id) throws DataAccessException;

    public boolean isLiked(Long popup_id, Long user_id) throws DataAccessException;

    public void updatePopup(Map popupMap) throws DataAccessException;

    public void updateImage(Map popupMap) throws DataAccessException;

    //사업자 신청 팝업 리스트
    public List selectBsPopup(@Param("count") int count, @Param("id") int id) throws DataAccessException;

    //사업자 신청 팝업 개수
    public int selectToBsPopup(int id) throws DataAccessException;

    public List<PopupDTO> selectMyPopup(@Param("count") int count, @Param("id") int id) throws DataAccessException;

    public int selectTotPopup(int id) throws DataAccessException;

    public int selectTooPopup(int id) throws DataAccessException;

    public ImageDTO selectFirstImg(Long popup_id) throws DataAccessException;

    public List<PopupDTO> bestPopup() throws DataAccessException;

    public List<HashTagDTO> bestHashTagList(boolean isLoggedIn, boolean userSelectedHashtags, Long id) throws DataAccessException;

//    public List<HashTagDTO> bestHashTagList() throws DataAccessException;

    public List<PopupDTO> searchPopupHasTag(String hashtag) throws DataAccessException;

    public List<PopupDTO> selectOngoingPopup(int count) throws DataAccessException;

    public List<PopupDTO> selectUpcomingPopup(int count) throws DataAccessException;

    public List<PopupDTO> selectEndPopup(int count) throws DataAccessException;

    //해시태그를 가져오는 메서드 추가
    List<String> selectPopupTags(Long popup_id) throws DataAccessException;

    public void updateHashTags(Map popupMap) throws DataAccessException;

    public Integer findHashTagId(String hashTag) throws DataAccessException;

    public void deletePopupHashTag(Long popupId) throws DataAccessException;

    //yoni

    public void getUserHashTags(long user_id) throws DataAccessException;

    public boolean hasSelectedHashtags(Long id) throws DataAccessException;
}