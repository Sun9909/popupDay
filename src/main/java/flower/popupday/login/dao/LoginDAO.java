package flower.popupday.login.dao;

import flower.popupday.login.dto.LoginDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Mapper //mapper로 LoginDAO와 연결
@Repository("loginDAO")
public interface LoginDAO {

    public void insertLogin(LoginDTO loginDTO) throws DataAccessException;

}