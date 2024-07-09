package flower.popupday.notice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("noticeService")
public class NotcieServiceImpl implements NoticeService{

    @Autowired
    private NoticeDAO noticeDAO;

    /*페이징처리
    @Override
    // Map은 기사 목록과 전체 기사가 포함됨.
    public Map notice(Map<String, Integer> pageingMap) throws DataAccessException {
    Map articleMap = new HashMap<>(); //결과를 저장할 새로운 hasmap 객체를 생성 */

    //여러개의 이미지 추가
    //articleMap 매개변수를 받아들이며 Map타입
    public int addArticle(Map articleMap) throws DataAccessException {
        // boardDAO 사용하여 새로운 글 번호 생성 > notice_id변수 저장.
        int notice_id = noticeDAO.getNewArticleNo();
        //글 번호 주입
        articleMap.put("notice_id",notice_id);
        //새로운 글을 데이터베이스에 삽입
        noticeDAO.inserNewArticle(articleMap);
        //이미지 파일 처리
        if (articleMap.get("imagesFileList") != null ) { //이미지 파일이 포함되어 있는지 확인 (null 값이 아닌지 확인)
            noticeDAO.insertNewImages(articleMap); // 이미지 파일 삽입(이미지 파일이 존재할 때만)
        }
        return notice_id; //글번호 반환(새로추가된 글번호)

    }
}
