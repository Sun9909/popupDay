package flower.popupday.business.dao;

import flower.popupday.business.dto.HitsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatsDAO {

//    public Map statsList(long popup_id) throws DataAccessException;

    public List<HitsDTO> statsCount(long popup_id) throws DataAccessException;

}
