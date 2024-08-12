package flower.popupday.point.service;

import flower.popupday.point.dao.PointDAO;
import flower.popupday.point.dto.PointDTO;
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

    @Override
    public int addGoods(PointDTO pointDTO) throws DataAccessException {
        int shop_id = pointDAO.getNewGoodsNo();
        pointDTO.setShop_id(shop_id);
        pointDAO.addGoods(pointDTO);
        return shop_id;
    }

    @Override
    public void removeGoods(int shop_id) throws DataAccessException {
        pointDAO.removeGoods(shop_id);
    }

    @Override
    public List getGoodsContent(int shop_id) throws DataAccessException {
        List goodsList = pointDAO.getGoodsList(shop_id);
        return goodsList;
    }
}
