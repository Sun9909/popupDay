package flower.popupday.admin.service;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface AdminService {
    public List memberShip() throws DataAccessException;

    public void delMember(Long id) throws DataAccessException;
}
