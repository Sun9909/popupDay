package flower.popupday.act.service;

import flower.popupday.act.dto.ActDTO;
import org.springframework.dao.DataAccessException;

public interface ActService {
    public ActDTO findMember(String id) throws DataAccessException;
}
