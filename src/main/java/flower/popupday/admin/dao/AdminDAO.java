package flower.popupday.admin.dao;

import flower.popupday.admin.dto.AdminDTO;
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

}
