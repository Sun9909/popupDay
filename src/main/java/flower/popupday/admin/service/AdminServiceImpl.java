package flower.popupday.admin.service;

import flower.popupday.admin.dao.AdminDAO;
import flower.popupday.admin.dto.AdminDTO;
import flower.popupday.notice.notice.dto.NoticeDTO;
import flower.popupday.popup.dto.ImageDTO;
import flower.popupday.popup.dto.PopupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDAO adminDAO;

//    @Override
//    public List memberShip() throws DataAccessException {
//        List membersList=adminDAO.selectAllMembersList();
//        return membersList;
//    }
    @Override
    public Map<String, Object> memberShip(Map<String, Integer> pagingMap) throws DataAccessException {
        Map<String, Object> memberMap = new HashMap<>(); // 공지사항 목록과 관련 데이터를 저장할 맵을 생성
        int section = pagingMap.get("section"); // pagingMap에서 section 값을 가져와 section 변수에 저장
        int pageNum = pagingMap.get("pageNum"); // pagingMap에서 pageNum 값을 가져와 pageNum 변수에 저장
        int count = (section -1) * 100 + (pageNum - 1) * 10; // section,pageNum을 사용해 DB쿼리의 시작 위치를 계산 -> section은 100개의 공지사항을 pageNum은 10개의 공지사항을 나타냄.
        List<AdminDTO> memberList = adminDAO.selectAllMemberList(count); //noticeDAO를 사용해 count 위치부터 공지사항 목록을 가져옴 -> NoticeDTO 객체의 리스트로 반환 됨 (전체 글 조회)
        int totmember = adminDAO.selectTotmember(); // noticeDAO를 사용해 전체 공지사항 수를 가져옴.

        memberMap.put("memberList", memberList); // noticeList를 noticeMap에 추가
        memberMap.put("totmember", totmember);   // totNotice를 noticeMap에 추가
        //memberMap.put("totmember", 324);

        // Debugging 로그 추가
        System.out.println("totmember: " + totmember);

        return memberMap;  // 공지사항 목록과 전체 공지사항 수를 포함한 noticeMap으로 반환
    }

    @Override
    public AdminDTO findMember(Long id) throws DataAccessException {
        AdminDTO adminDTO=adminDAO.selectMemberById(id);
        return adminDTO;
    }

    @Override
    public void updateMember(AdminDTO adminDTO) throws DataAccessException {
        adminDAO.updateMember(adminDTO);
    }

    @Override
    public void delMember(Long id) throws DataAccessException {
        adminDAO.delMember(id);
    }
}
