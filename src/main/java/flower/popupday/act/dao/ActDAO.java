package flower.popupday.act.dao;

import flower.popupday.act.dto.ActDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Mapper // 실행시 mapper namespace 연결
@Repository("actDAO")
public interface ActDAO {

    public ActDTO selectMemberById(String id) throws DataAccessException;
}
