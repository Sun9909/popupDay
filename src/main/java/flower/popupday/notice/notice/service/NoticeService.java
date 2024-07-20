package flower.popupday.notice.notice.service;

import org.springframework.dao.DataAccessException;


import java.util.Map;
public interface NoticeService {

    //글 목록, 페이징처리
    public Map noticeList(Map<String, Integer> pagingMap) throws DataAccessException;

    // 글쓰기 + 이미지 여러개 추가
    public int addNotice(Map<String, Object> noticeMap) throws DataAccessException;

    // 여러개의 글과 이미지 상세 글보기
    public Map noticeView(long notice_id) throws DataAccessException;

    // 여러개의 글과 이미지 수정
    public void modNotice(Map noticeMap) throws DataAccessException;

    // 글,이미지 삭제
    public void removeNotice(long notice_id) throws DataAccessException;

}
