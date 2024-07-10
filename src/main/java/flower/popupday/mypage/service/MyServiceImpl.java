package flower.popupday.mypage.service;

import flower.popupday.mypage.dao.MyDAO;
import flower.popupday.mypage.dto.MyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myService")
public class MyServiceImpl implements MyService {

    @Autowired
    private MyDAO myDAO;

//    @Override
//    public List membersList() throws DataAccessException {
//        List membersList=myDAO.findAllList();
//        return membersList;
//    }

    @Override // 한사람 정보 = 리스트 , 세션 이미지 + 유저정보 map
    public MyDTO getName(MyDTO myDTO) throws DataAccessException {
        MyDTO getName= myDAO.getName(myDTO);
        return getName;

    }

}
