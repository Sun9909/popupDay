package flower.popupday.admin.service;

import flower.popupday.admin.dao.AdminDAO;
import flower.popupday.admin.dto.AdminDTO;
import flower.popupday.popup.dto.HashTagDTO;
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

    @Override
    public Map<String, Object> registerList(Map<String, Integer> pagingMap) throws DataAccessException {
        Map<String, Object> popupListMap = new HashMap<>();
        int section = pagingMap.get("section");
        int pageNum = pagingMap.get("pageNum");
        int count = (section - 1) * 100 + (pageNum - 1) * 10;
        List<PopupDTO> popupList = adminDAO.pickAllPopup(count);
        int totPopup = adminDAO.pickToPopup();

        List<Map<String, Object>> popupInfoList = new ArrayList<>();
        for (PopupDTO popup : popupList) {
            Long popup_id = popup.getPopup_id();
            ImageDTO thumbnailImage = adminDAO.selectFirstImage(popup_id);
            Map<String, Object> popupInfo = new HashMap<>();
            popupInfo.put("popup", popup); // 팝업 정보 추가
            popupInfo.put("thumbnailImage", thumbnailImage);
            popupInfoList.add(popupInfo);
        }

        popupListMap.put("popupInfoList", popupInfoList); // 팝업 정보 리스트 추가
        popupListMap.put("totPopup", totPopup);
        return popupListMap;
    }

    @Override
    public Map<String, Object> register(Long popup_id) throws DataAccessException {
        Map<String, Object> popupMap = new HashMap<>();
        //신청된 팝업
        PopupDTO popupList = adminDAO.selectRegisterPopup(popup_id);
        List<ImageDTO> imageFileList = adminDAO.selectImageFileList(popup_id);
        List<HashTagDTO> hashTagList = adminDAO.selectHashTagList(popup_id);

        popupMap.put("popupList", popupList);
        popupMap.put("imageFileList", imageFileList);
        popupMap.put("hashTagList", hashTagList);

        return popupMap;
    }

    @Override
    public void roleUpdate(Long popup_id, String role) throws DataAccessException {
        adminDAO.roleUpdate(popup_id, role);
    }
}
