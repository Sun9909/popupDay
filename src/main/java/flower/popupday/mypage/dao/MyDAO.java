package flower.popupday.mypage.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("myDAO")
public interface MyDAO {
    public List findAllList() throws DataAccessException;

    public void findId(Long id) throws DataAccessException;

    public void findName(String user_nikname) throws DataAccessException;
}
