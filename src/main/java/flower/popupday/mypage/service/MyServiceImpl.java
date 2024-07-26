package flower.popupday.mypage.service;

import flower.popupday.login.dto.LoginDTO;
import flower.popupday.mypage.dao.MyDAO;
import flower.popupday.mypage.dto.MyDTO;
import flower.popupday.mypage.dto.MyPopupDTO;
import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String getreCommentCount(String user_nickname) {
        String recommentCount = myDAO.getreCommentCount(user_nickname);
        return recommentCount;
    }

    @Override   //팝업 댓글 개수
    public String getpopCommentCount(String user_nickname) {
        String popcommentCount = myDAO.getpopCommentCount(user_nickname);
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
        MyDTO myDTO=myDAO.selectMemberById(id); //DAO 메소드 호출하여 사용자 정보 조회
        return myDTO;
        //return myDAO.selectMemberById(id);
    }

    @Override
    public void updateLogin(LoginDTO loginDTO) throws DataAccessException {
        myDAO.updateLogin(loginDTO);
    }

    @Override
    public void updatePwd(LoginDTO loginDTO) throws DataAccessException {
        myDAO.updatePwd(loginDTO);
    }

    @Override
    public void dropMember(LoginDTO loginDTO) throws DataAccessException {
        myDAO.dropMember(loginDTO);
    }

    @Override
    public Map<String, Object> myPopupLike(Map<String, Integer> pagingMap) throws DataAccessException {
        Map<String, Object> popupMap = new HashMap<>();
        int section = pagingMap.get("section");
        int pageNum = pagingMap.get("pageNum");
        int id = pagingMap.get("id");
        int count = (section - 1) * 100 + (pageNum - 1) * 10; // 현재 섹션에는 1
        List<PopupDTO> popupList = myDAO.selectMyPopup(count, id); // 팝업 목록 조회
        int totPopup = myDAO.selectToPopup(); // 전체 팝업 수 조회

        List<Map<String, Object>> popupLike = new ArrayList<>();
        for (PopupDTO popup : popupList) {
            Long popup_id = popup.getPopup_id();
            ImageDTO thumbnailImage = myDAO.selectFirstImage(popup_id); // 각 팝업의 첫 번째 이미지 조회
            Map<String, Object> popupInfo = new HashMap<>();
            popupInfo.put("popup", popup); // 팝업 정보 추가
            popupInfo.put("thumbnailImage", thumbnailImage); // 이미지 정보 추가
            popupLike.add(popupInfo);
        }

        popupMap.put("popupLike", popupLike); // 팝업 정보 리스트 추가
        popupMap.put("totPopup", totPopup);

        // 디버깅 로그 추가
        System.out.println("popupLike: " + popupLike);
        System.out.println("totPopup in service: " + totPopup);

        return popupMap;
    }

    @Override
    public boolean checkEmail(String email) {
        return myDAO.checkEmail(email);
    }

    @Override
    public boolean checknickname(String user_nickname) {
        return myDAO.checknickname(user_nickname);
    }


}