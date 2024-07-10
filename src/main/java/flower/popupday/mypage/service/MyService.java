package flower.popupday.mypage.service;

import flower.popupday.mypage.dto.MyDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface MyService {
    //public List membersList() throws DataAccessException;

    public MyDTO getName(MyDTO myDTO) throws DataAccessException;

}
