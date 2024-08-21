package flower.popupday.business.service;

import flower.popupday.business.dao.StatsDAO;
import flower.popupday.business.dto.HitsDTO;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("statsService")
public class StatsServiceImpl implements StatsService{

    @Autowired
    StatsDAO statsDAO;

//    @Override
//    public Map statsList(long popup_id) throws DataAccessException {
//
//        Map visitList = statsDAO.statsList(popup_id);
//
//        return visitList;
//    }

    @Override
    public List<HitsDTO> statsCount(long popup_id) throws DataAccessException {
        List<HitsDTO> visitCount = statsDAO.statsCount(popup_id);
        return visitCount;
    }

    @Override
    public void updateHitUser(long popup_id, long id) throws DataAccessException {
        statsDAO.updateHitUser(popup_id,id);
    }

}
