package flower.popupday.notice.notice.service;

import org.springframework.dao.DataAccessException;

import java.util.Map;

public interface NoticeService {

    // 글목록 페이징처리
    public Map<String, Object> notice(Map<String, Integer> pagingMap) throws DataAccessException;

    // 여러개의 이미지 추가
    public int addNotice(Map<String, Object> articleMap) throws DataAccessException;

    // 상세글 보기 (글번호를 가지고 상세 글 보기)
    public Map<String, Object> adminNoticeView(Long notice_id) throws DataAccessException;

    // 여러개의 이미지 글 수정
    public void modArticle(Map<String, Object> articleMap) throws DataAccessException;

    // 글삭제
    public void removeArticle(Long notice_id) throws DataAccessException;
}
