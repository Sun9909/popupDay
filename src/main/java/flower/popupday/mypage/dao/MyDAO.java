package flower.popupday.mypage.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface MyDAO {
    public List findAllList() throws DataAccessException;

    public void findId(Long id) throws DataAccessException;

    public void findName(String user_nikname) throws DataAccessException;
}
