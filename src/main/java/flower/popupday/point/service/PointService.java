package flower.popupday.point.service;

import flower.popupday.point.dto.PointDTO;
import org.springframework.dao.DataAccessException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface PointService {

    public List pointList() throws DataAccessException;

    public int addGoods(PointDTO pointDTO) throws DataAccessException;

    public void removeGoods(int shop_id) throws DataAccessException;

    public List getGoodsContent(int shop_id) throws DataAccessException;

    public void modGoods(PointDTO pointDTO) throws DataAccessException;

    public List getGiftList(List giftList) throws DataAccessException;


}
