package flower.popupday.point.service;

import flower.popupday.point.dto.PointDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface PointService {

    public List pointList() throws DataAccessException;

    public int addGoods(PointDTO pointDTO) throws DataAccessException;
}
