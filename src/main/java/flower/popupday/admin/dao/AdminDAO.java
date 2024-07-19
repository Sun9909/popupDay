package flower.popupday.admin.dao;

import flower.popupday.admin.dto.AdminDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface AdminDAO {
    public List selectAllMembersList() throws DataAccessException;

    public AdminDTO selectMemberById(Long id) throws DataAccessException;

    public void updateMember(AdminDTO adminDTO) throws DataAccessException;

    public void delMember(Long id) throws DataAccessException;

    boolean checkId(String user_id) throws DataAccessException;

    boolean checkNikname(String user_nikname) throws DataAccessException;
}
