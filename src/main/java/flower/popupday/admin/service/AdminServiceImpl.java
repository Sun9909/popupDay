package flower.popupday.admin.service;

import flower.popupday.admin.dao.AdminDAO;
import flower.popupday.admin.dto.AdminDTO;
import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public AdminDTO findMember(Long id) throws DataAccessException {
        AdminDTO adminDTO=adminDAO.selectMemberById(id);
        return adminDTO;
    }

    @Override
    public void updateMember(AdminDTO adminDTO) throws DataAccessException {
        adminDAO.updateMember(adminDTO);
    }

    @Override
    public void delMember(Long id) throws DataAccessException {
        adminDAO.delMember(id);
    }

    @Override
    public boolean checkId(String user_id) {
        return adminDAO.checkId(user_id);
    }

    @Override
    public boolean checkNikname(String user_nikname) {
        return adminDAO.checkNikname(user_nikname);
    }


}
