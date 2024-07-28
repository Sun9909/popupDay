package flower.popupday.notice.notice.service;

import flower.popupday.notice.notice.dao.NoticeDAO;
import flower.popupday.notice.notice.dto.NoticeDTO;
import flower.popupday.notice.notice.dto.NoticeimageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDAO noticeDAO;

    // 공지사항 추가
    public long addNotice(Map<String, Object> noticeMap) throws DataAccessException {
        long notice_id = noticeDAO.getNotice_id();
        noticeMap.put("notice_id", notice_id);
        noticeDAO.insertNewNotice(noticeMap); // notice_id를 받아오는 메서드

        if(noticeMap.get("imageFileList") != null) {
            noticeDAO.insertNewImages(noticeMap);
        }
        return notice_id;
    }


    public void addimageNotice(Map<String,Object> noticeMap) throws DataAccessException {
        noticeDAO.insertNewImages(noticeMap);
    }

    //글 목록, 페이징처리
    @Override
    public Map<String, Object> noticeList(Map<String, Integer> pagingMap) throws DataAccessException {
        Map<String, Object> noticeMap = new HashMap<>(); // 공지사항 목록과 관련 데이터를 저장할 맵을 생성

        int section = pagingMap.get("section"); // pagingMap에서 section 값을 가져와 section 변수에 저장
        int pageNum = pagingMap.get("pageNum"); // pagingMap에서 pageNum 값을 가져와 pageNum 변수에 저장

        int count = (section -1) * 100 + (pageNum - 1) * 10; // section,pageNum을 사용해 DB쿼리의 시작 위치를 계산 -> section은 100개의 공지사항을 pageNum은 10개의 공지사항을 나타냄.

        List<NoticeDTO> noticeList = noticeDAO.selectAllNotice(count); //noticeDAO를 사용해 count 위치부터 공지사항 목록을 가져옴 -> NoticeDTO 객체의 리스트로 반환 됨 (전체 글 조회)
        int totNotice = noticeDAO.selectTotNotice(); // noticeDAO를 사용해 전체 공지사항 수를 가져옴.

        noticeMap.put("noticeList", noticeList); // noticeList를 noticeMap에 추가
        noticeMap.put("totNotice", totNotice);   // totNotice를 noticeMap에 추가
        //noticeMap.put("totNotice", 324);

        // Debugging 로그 추가
        System.out.println("noticeList: " + noticeList);
        System.out.println("totNotice: " + totNotice);

        return noticeMap;  // 공지사항 목록과 전체 공지사항 수를 포함한 noticeMap으로 반환
    }


    // 공지사항 상세보기
    @Override
    public Map noticeView(long notice_id) throws DataAccessException {
        Map noticeMap = new HashMap();
        NoticeDTO noticeDTO = noticeDAO.selectNotice(notice_id);  // noticeDAO를 사용해 notice_id에 해당하는 공지사항 정보를 가져옴

        List<NoticeimageDTO> imageFileList = noticeDAO.selectImageFileList(notice_id); // noticeDAO를 사용해 notice_id에 해당하는 이미지 파일 리스트를 가져옴
        System.out.println("noticeview :" + imageFileList);

        // noticeDTO와 imageFileList를 noticeMap에 추가
        noticeMap.put("imageFileList" ,imageFileList);
        noticeMap.put("notice", noticeDTO);
        return noticeMap;
    }

    // 공지사항 수정반영하기
    @Override
    public void modNotice(Map noticeMap) throws DataAccessException {
        noticeDAO.updateNotice(noticeMap); // 글수정
        noticeDAO.updateImage(noticeMap); // 이미지 수정
    }


    // 후기 삭제하기
    @Override
    public void removeNotice(long notice_id) throws DataAccessException {
        noticeDAO.deleteImage(notice_id);
        noticeDAO.deleteNotice(notice_id);
    }


}