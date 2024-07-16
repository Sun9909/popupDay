package flower.popupday.notice.notice.service;

import flower.popupday.notice.notice.dao.NoticeDAO;
import flower.popupday.notice.notice.dto.NoticeDTO;
import flower.popupday.notice.notice.dto.NoticeimageDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("noticeService")
public class NotcieServiceImpl implements NoticeService {

    @Autowired
    private NoticeDAO noticeDAO;

    public void removeNotice(Long notice_Id, HttpSession session) {
      if (!isAdmin(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자 권한이 필요합니다.");
        }
        // 권한이 있는 경우 공지사항 삭제 로직 실행
        noticeDAO.removeNotice(notice_Id);
        // 이미지 디렉토리 삭제 등의 추가 작업
    }
    private boolean isAdmin(HttpSession session) {
        // 세션에서 사용자 역할을 가져와서 관리자 권한을 확인하는 로직
        String role = (String) session.getAttribute("userRole");
        return role != null && role.equals("ADMIN");
    }



    // 페이징 처리
    @Override
    public Map<String, Object> noticeList(Map<String, Integer> pagingMap) throws DataAccessException {
        Map<String, Object> noticeMap = new HashMap<>(); // 공지상황 목록과 관련 데이터를 저장할 맵을 생성
        int section = pagingMap.get("section"); // pagingMap에서 section 값을 가져와 section 변수에 저장
        int pageNum = pagingMap.get("pageNum"); // pagingMap에서 pageNum 값을 가져와 pageNum 변수에 저장
        int count = (section - 1) * 100 + (pageNum - 1) * 10; // section,pageNum을 사용해 DB쿼리의 시작 위치를 계산 -> section은 100개의 공지사항을 pageNum은 10개의 공지사항을 나타냄.
        List<NoticeDTO> noticeList = noticeDAO.selectAllNotice(count); // noticeDAO를 사용해 count 위치부터 공지사항 목록을 가져옴 -> NoticeDTO 객체의 리스트로 반환 됨
        int totNotice = noticeDAO.selectTotalNotice(); // noticeDAO를 사용해 전체 공지사항 수를 가져옴.
        noticeMap.put("noticeList", noticeList); // noticeList를 noticeMap에 추가
        noticeMap.put("totNotice", totNotice); // totNotice를 noticeMap에 추가

        // Debugging 로그 추가
        System.out.println("noticeList: " + noticeList);
        System.out.println("totNotice: " + totNotice);

        return noticeMap; //공지사항 목록과 전체 공지사항 수를 포함한 noticeMap을 반환
    }

    // 여러 개의 이미지 추가
    public int addNotice(Map<String, Object> noticeMap) throws DataAccessException {
//        int notice_id = notic  public void removeNotice(Long noticeId, HttpSession session) {
//        if (!isAdmin(session)) {
//            throw new UnauthorizedAccessException("관리자 권한이 필요합니다.");
//        }
//        // 권한이 있는 경우 공지사항 삭제 로직 실행
//        noticeDAO.removeNotice(noticeId);
//        // 이미지 디렉토리 삭제 등의 추가 작업
//    eDAO.getNewNoticeNo();
//        noticeMap.put("notice_id", notice_id);
        int notice_id = noticeDAO.insertNewNotice(noticeMap);
        if (noticeMap.get("imagesFileList") != null) {
            noticeDAO.insertNewImages(noticeMap);
        }
        return notice_id;
    }

    // 여러 개의 이미지 상세 글 보기
    @Override
    public Map<String, Object> adminNoticeView(Long notice_id) throws DataAccessException {
        Map<String, Object> noticeMap = new HashMap<>();
        NoticeDTO noticeDTO = noticeDAO.selectNotice(notice_id);
        List<NoticeimageDTO> imageFileList = noticeDAO.selectImageFileList(notice_id);
        noticeMap.put("notice", noticeDTO);
        noticeMap.put("imageFileList", imageFileList);
        return noticeMap;
    }

    @Override
    public void removeNotice(Long notice_id) throws DataAccessException {
        noticeDAO.deleteNotice(notice_id);
        noticeDAO.deleteImage(notice_id);
    }

    @Override
    public void modNotice(Map<String, Object> noticeMap) throws DataAccessException {
        noticeDAO.updateNotice(noticeMap);
        noticeDAO.updateImage(noticeMap);
    }

}

