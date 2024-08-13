package flower.popupday.point.dao;

import flower.popupday.point.dto.PointDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface PointDAO {

    public List getpointList() throws DataAccessException;

    public int getNewGoodsNo() throws DataAccessException;

    public void addGoods(PointDTO pointDTO) throws DataAccessException;

    public void removeGoods(int shop_id) throws DataAccessException;

    public List getGoodsList(int shop_id) throws DataAccessException;

    public void modGoods(PointDTO pointDTO) throws DataAccessException;

}
