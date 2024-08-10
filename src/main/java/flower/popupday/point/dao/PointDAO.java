package flower.popupday.point.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface PointDAO {

    public List getpointList() throws DataAccessException;
}
