package flower.popupday.notice.notice.dao;

import flower.popupday.notice.notice.dto.NoticeDTO;
import flower.popupday.notice.notice.dto.NoticeimageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeDAO {

    // 전체 글 조회(매개변수받아 이름 재정의)
    public List selectAllNotice(@Param("count") int count) throws DataAccessException;

    // 페이징 (전체 글번호 조회)
    public int selectTotNotice() throws DataAccessException;

    // 글 + 이미지 추가(새 글 추가)
    public int insertNewNotice(Map noticeMap) throws DataAccessException;

    // 여러개 이미미 추가
    public void insertNewImages(Map noticeMap) throws DataAccessException;

    // 여러개의 글과 이미지 상세 글보기
    public NoticeDTO selectNotice(long notice_id) throws DataAccessException;
    public List<NoticeimageDTO> selectImageFileList(long notice_id) throws DataAccessException;

    // 여러개이 글과 이미지 수정
    public void updateNotice(Map noticeMap) throws DataAccessException;
    public void updateImage(Map noticeMap) throws DataAccessException;

    // 여러개의 글과 이미지 삭제
    public void deleteImage(long notice_id) throws DataAccessException;
    public void deleteNotice(long notice_id) throws DataAccessException;

    public long getNotice_id()  throws DataAccessException;
}
