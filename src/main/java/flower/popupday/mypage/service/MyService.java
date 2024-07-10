package flower.popupday.mypage.service;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface MyService {
    public List membersList() throws DataAccessException;

    public void findId(Long id) throws DataAccessException;

    public void findName(String user_nikname) throws DataAccessException;
}
