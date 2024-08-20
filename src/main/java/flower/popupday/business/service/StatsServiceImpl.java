package flower.popupday.business.service;

import flower.popupday.business.dao.StatsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("statsService")
public class StatsServiceImpl implements StatsService{

    @Autowired
    StatsDAO statsDAO;

    @Override
    public List statsList(long popup_id) throws DataAccessException {

        List visitList = statsDAO.statsList(popup_id);

        return visitList;
    }

    @Override
    public int statsCount(long popup_id) throws DataAccessException {
        int visitCount = statsDAO.statsCount(popup_id);
        return visitCount;
    }
}
