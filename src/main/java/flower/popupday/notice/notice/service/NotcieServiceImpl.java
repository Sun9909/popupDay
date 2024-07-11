//package flower.popupday.notice.notice.service;
//
//import flower.popupday.notice.notice.dto.NoticeDTO;
//import flower.popupday.notice.notice.dto.NoticeimageDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service("noticeService")
//public class NotcieServiceImpl implements NoticeService {
//
//    @Autowired
//    private NoticeDTO noticeDAO;
//
//    //페이징처리
//    @Override
//    public Map<String, Object> notice(Map<String, Integer> pagingMap) throws DataAccessException {
//        Map<String, Object> articleMap = new HashMap<>(); // 결과를 저장할 새로운 HashMap 객체를 생성
//        List<NoticeDTO> articlesList = noticeDAO.selectAllArticles(pagingMap); // 페이징 맵을 사용하여 모든 기사를 선택
//        int totalArticles = noticeDAO.selectTotalArticles(); // 전체 기사 수를 선택
//        articleMap.put("articlesList", articlesList); // 기사 목록을 저장
//        articleMap.put("totalArticles", totalArticles); // 전체 기사 수를 저장
//        return articleMap;
//    }
//
//    //여러개의 이미지 추가
//    //articleMap 매개변수를 받아들이며 Map타입
//    public int addNotice(Map articleMap) throws DataAccessException {
//        // boardDAO 사용하여 새로운 글 번호 생성 > notice_id변수 저장.
//        int notice_id = noticeDAO.getNewArticleNo();
//        //글 번호 주입
//        articleMap.put("notice_id",notice_id);
//        //새로운 글을 데이터베이스에 삽입
//        noticeDAO.insertNewArticle(articleMap);
//        //이미지 파일 처리
//        if (articleMap.get("imagesFileList") != null ) { //이미지 파일이 포함되어 있는지 확인 (null 값이 아닌지 확인)
//            noticeDAO.insertNewImages(articleMap); // 이미지 파일 삽입(이미지 파일이 존재할 때만)
//        }
//        return notice_id; //글번호 반환(새로추가된 글번호)
//    }
//
//    // 여러개의 이미지 상세 글보기
//    @Override
//    public Map adminNoticeView(Long notice_id) throws DataAccessException {
//        Map articleMap=new HashMap();
//        NoticeDTO noticeDTO = noticeDAO.selectArticles(notice_id); // 글번호를 가지고 셀렉트문
//        List<NoticeimageDTO> imageFileList = noticeDAO.selectImageFileList(notice_id); // 글번호를 가지고 이미지찾음
//        articleMap.put("notice", noticeDTO); // 정보
//        articleMap.put("imageFileList", imageFileList); // 이미지 글번호
//        return articleMap;
//    }
//
//    // 여러개의 이미지글 수정하기
//    @Override
//    public void modArticle(Map articleMap) throws DataAccessException {
//        noticeDAO.updateArticle(articleMap); // 글수정
//        noticeDAO.updateImage(articleMap); // 이미지 수정
//    }
//
//    @Override
//    public void removeArticles(Long notice_id) throws DataAccessException {
//        noticeDAO.deleteImage(notice_id);
//        noticeDAO.deleteArticle(notice_id);
//    }
//}
