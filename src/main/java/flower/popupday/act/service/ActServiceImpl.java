package flower.popupday.act.service;

import flower.popupday.act.dao.ActDAO;
import flower.popupday.act.dto.ActDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service("actService")
public class ActServiceImpl implements ActService{
    @Autowired
    private ActDAO actDAO;

    @Override
    public ActDTO findMember(String id) throws DataAccessException {
        ActDTO actDTO= actDAO.selectMemberById(id);
        return actDTO;
    }
}
