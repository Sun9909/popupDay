package flower.popupday.admin.service;

import flower.popupday.admin.dao.AdminDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDAO adminDAO;

    @Override
    public List memberShip() throws DataAccessException {
        List membersList=adminDAO.selectAllMembersList();
        return membersList;
    }

    @Override
    public void delMember(Long id) throws DataAccessException {
        adminDAO.delMember(id);
    }
}
