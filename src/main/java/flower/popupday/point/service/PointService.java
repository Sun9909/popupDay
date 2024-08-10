package flower.popupday.point.service;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface PointService {

    public List pointList() throws DataAccessException;
}
