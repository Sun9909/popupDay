package flower.popupday.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface AdminDAO {
    public List selectAllMembersList() throws DataAccessException;

    public void delMember(Long id) throws DataAccessException;
}
