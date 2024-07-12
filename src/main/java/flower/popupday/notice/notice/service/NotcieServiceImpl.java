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
public class NotcieServiceImpl implements NoticeService {

    @Autowired
    private NoticeDAO noticeDAO;

    //페이징처리
    @Override
    public Map noticeList(Map<String, Integer> pageingMap) throws DataAccessException {
        Map noticeMap=new HashMap<>();
        int section=pageingMap.get("section");
        int pageNum=pageingMap.get("pageNum");
        int count=(section-1)*100+(pageNum-1)*10; // 현재 섹션에는 1
        List<NoticeDTO> noticeList=noticeDAO.selectAllNotice(count); // boardDAO.메서드 호출 dto로 받음
        int totArticles=noticeDAO.selectTotalArticles(); // 전체 글 목록 수 조회
        noticeMap.put("noticeList", noticeList);
        //articleMap.put("totArticles", totArticles); 진짜 데이터 있을때 쓰면 됨
        noticeMap.put("totNotice", 324); // 페이징처리 확인을 위해 가상 글 324개
        return noticeMap;
    }

    //여러개의 이미지 추가
    //articleMap 매개변수를 받아들이며 Map타입
    public int addNotice(Map noticeMap) throws DataAccessException {
        // boardDAO 사용하여 새로운 글 번호 생성 > notice_id변수 저장.
        long notice_id = noticeDAO.getNewArticleNo();
        //글 번호 주입
        noticeMap.put("notice_id",notice_id);
        //새로운 글을 데이터베이스에 삽입
        noticeDAO.insertNewArticle(noticeMap);
        //이미지 파일 처리
        if (noticeMap.get("imagesFileList") != null ) { //이미지 파일이 포함되어 있는지 확인 (null 값이 아닌지 확인)
            noticeDAO.insertNewImages(noticeMap); // 이미지 파일 삽입(이미지 파일이 존재할 때만)
        }
        return notice_id; //글번호 반환(새로추가된 글번호)
    }

    // 여러개의 이미지 상세 글보기
    @Override
    public Map adminNoticeView(Long notice_id) throws DataAccessException {
        Map noticeMap=new HashMap();
        NoticeDTO noticeDTO = noticeDAO.selectArticles(notice_id); // 글번호를 가지고 셀렉트문
        List<NoticeimageDTO> imageFileList = noticeDAO.selectImageFileList(notice_id); // 글번호를 가지고 이미지찾음
        noticeMap.put("notice", noticeDTO); // 정보
        noticeMap.put("imageFileList", imageFileList); // 이미지 글번호
        return noticeMap;
    }

    @Override
    public void removeNotice(Long notice_id) throws DataAccessException {

    }

    // 여러개의 이미지글 수정하기
    @Override
    public void modNotice(Map noticeMap) throws DataAccessException {

    }

    @Override
    public void removeNotice(Long notice_id) throws DataAccessException {

    }
}
