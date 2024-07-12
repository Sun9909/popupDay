package flower.popupday.mypage.service;

import flower.popupday.mypage.dao.MyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myService")
public class MyServiceImpl implements MyService {

    @Autowired
    private MyDAO myDAO;

    @Override
    public List membersList() throws DataAccessException {
        List membersList=myDAO.findAllList();
        return membersList;
    }

    @Override
    public void findId(Long id) throws DataAccessException {

    }

    @Override
    public void findName(String user_nikname) {

    }
}
