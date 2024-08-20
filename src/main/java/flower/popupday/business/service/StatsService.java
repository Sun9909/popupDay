package flower.popupday.business.service;

import org.knowm.xchart.XYChart;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface StatsService {

    public List statsList(long popup_id) throws DataAccessException;

    public int statsCount(long popup_id) throws DataAccessException;

}
