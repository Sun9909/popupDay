package flower.popupday.point.service;

import flower.popupday.point.dao.PointDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pointService")
public class PointServiceImpl implements PointService{

    @Autowired
    private PointDAO pointDAO;

    @Override
    public List pointList() throws DataAccessException {
        List pointList =  pointDAO.getpointList();

        return pointList;
    }
}
