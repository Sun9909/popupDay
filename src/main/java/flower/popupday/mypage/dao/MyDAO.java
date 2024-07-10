package flower.popupday.mypage.dao;

import flower.popupday.mypage.dto.MyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface MyDAO {
   // public List findAllList() throws DataAccessException;

    public MyDTO getName(MyDTO myDTO) throws DataAccessException;
}
