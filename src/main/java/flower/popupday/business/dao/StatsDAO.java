package flower.popupday.business.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface StatsDAO {

    public List statsList(long popup_id) throws DataAccessException;

    public int statsCount(long popup_id) throws DataAccessException;

}
