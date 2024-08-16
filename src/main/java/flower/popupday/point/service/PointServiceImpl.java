package flower.popupday.point.service;

import flower.popupday.point.dao.PointDAO;
import flower.popupday.point.dto.PointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

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
    public int userPoint(long id) throws DataAccessException {
        int userPoint = pointDAO.getUserPoint(id);
        return userPoint;
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

    @Override
    public void modGoods(PointDTO pointDTO) throws DataAccessException {
        pointDAO.modGoods(pointDTO);
    }

    @Override
    public void getGiftList(Map giftMap) throws DataAccessException {
        pointDAO.getGiftList(giftMap); //gifticon 추가
        pointDAO.pointUseList(giftMap); //포인트 사용내역 추가()
        pointDAO.usedPoint(giftMap); //포인트 사용내역 업데이트(tot_point)

    }


}
