package flower.popupday.mypage.service;

import flower.popupday.mypage.dao.MyDAO;
import flower.popupday.mypage.dto.MyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service("myService")
public class MyServiceImpl implements MyService {

    @Autowired
    private MyDAO myDAO;

    @Override // 한사람 정보 = 리스트 , 세션 이미지 + 유저정보 map
    public MyDTO getName(MyDTO myDTO) throws DataAccessException {
        MyDTO getName= myDAO.getName(myDTO);
        return getName;
    }

    @Override
    public int getReview() throws DataAccessException {
        int reviewCount = myDAO.selectAllReview();
        return reviewCount;
    }

    @Override
    public Long getReviewCount(Long id) {
        Long reviewCount = myDAO.getReviewCount(id);
        return reviewCount;
    }

    @Override
    public String getreCommentCount(String user_nikname) {
        String recommentCount = myDAO.getreCommentCount(user_nikname);
        return recommentCount;
    }

    @Override
    public String getpopCommentCount(String user_nikname) {
        String popcommentCount = myDAO.getpopCommentCount(user_nikname);
        return popcommentCount;
    }

}
