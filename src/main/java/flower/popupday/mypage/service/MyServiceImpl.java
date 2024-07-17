package flower.popupday.mypage.service;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.mypage.dao.MyDAO;
import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myService")
public class MyServiceImpl implements MyService {

    @Autowired
    private MyDAO myDAO;
    @Autowired
    private MyPopupDTO mypopupDTO;

    //마이페이지
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

    @Override   //리뷰 개수
    public Long getReviewCount(Long id) {
        Long reviewCount = myDAO.getReviewCount(id);
        return reviewCount;
    }

    @Override   //리뷰 댓글 개수
    public String getreCommentCount(String user_nikname) {
        String recommentCount = myDAO.getreCommentCount(user_nikname);
        return recommentCount;
    }

    @Override   //팝업 댓글 개수
    public String getpopCommentCount(String user_nikname) {
        String popcommentCount = myDAO.getpopCommentCount(user_nikname);
        return popcommentCount;
    }

    @Override   //문의 개수
    public Long getQnaCount(Long id) throws DataAccessException {
        Long qnaCount = myDAO.getQnaCount(id);
        return qnaCount;
    }

    //수정
    @Override
    public MyDTO findMember(Long id) throws DataAccessException {
        MyDTO myDTO=myDAO.selectMemberById(id);
        return myDTO;
    }


    //팝업 리스트
//    @Override
//    public MyPopupDTO getPopup(MyPopupDTO mypopupDTO) throws DataAccessException {
//        MyPopupDTO getPopup= myDAO.getPopup(mypopupDTO);
//        return getPopup;
//    }

    @Override
    public List<MyPopupDTO> getPopup(String user_id) throws DataAccessException {
        return myDAO.getPopup(user_id);
    }

    @Override
    public Long getPopupCount(Long user_id) throws DataAccessException {
        Long PopupCount = myDAO.getPopupCount(user_id);
        return PopupCount;
    }
}
