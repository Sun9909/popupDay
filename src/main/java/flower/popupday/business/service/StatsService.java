package flower.popupday.business.service;

import flower.popupday.business.dto.HitsDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface StatsService {

//    public Map statsList(long popup_id) throws DataAccessException;

    public List<HitsDTO> statsCount(long popup_id) throws DataAccessException;

    public void updateHitUser(long popup_id, long id) throws DataAccessException;

}
