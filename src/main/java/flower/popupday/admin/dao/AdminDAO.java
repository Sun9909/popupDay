package flower.popupday.admin.dao;

import flower.popupday.admin.dto.AdminDTO;
import flower.popupday.popup.dto.HashTagDTO;
import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface AdminDAO {
    public List selectAllMemberList(@Param("count") int count) throws DataAccessException;

    public int selectTotmember() throws DataAccessException;

    public AdminDTO selectMemberById(Long id) throws DataAccessException;

    public void updateMember(AdminDTO adminDTO) throws DataAccessException;

    public void delMember(Long id) throws DataAccessException;

    public List pickAllPopup(@Param("count") int count) throws DataAccessException;

    public int pickToPopup() throws DataAccessException;

    public ImageDTO selectFirstImage(Long popup_id) throws DataAccessException;

    public PopupDTO selectRegisterPopup(Long popup_id) throws DataAccessException;

    public List<ImageDTO> selectImageFileList(Long popup_id) throws DataAccessException;

    public List<HashTagDTO> selectHashTagList(Long popup_id) throws DataAccessException;

    public void roleUpdate(Long popup_id, String role) throws DataAccessException;
}
