package flower.popupday.notice.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
    @Repository("notcieDAO") // DB처리
    public interface NotcieDAO {

    // board.xml 쿼리문을 직접적으로 인터페이스에 연결을 위해 설정

    // 전체 글 조회 (매개변수받아 이름 재정의)
    public List selectAllArticles(@Param("count") int count) throws DataAccessException;

    // 페이징 (전체 글번호를 조회해서)
    public int selectToArticles() throws DataAccessException;

    // 글 번호 생성
    public int getNewArticleNo() throws DataAccessException;

    // 새 글 추가 (글번호 생성 후 새 글 추가 호출) xml 에서는 Map,DTO(둘중하나)로 받고 줄때는 DTO로 (여러개 이미지)
    public void insertNewArticle(Map articleMap) throws DataAccessException;

    // 이미지 손대는 메서드
    public void insertNewImages(Map articleMap) throws DataAccessException;

    // 상세 글 보기(글번호를 받아서 해당 글번호에 글 조회)
    public NotcieDTO selectArticles(Long notice_id) throws DataAccessException;

    public List selectImageFileList(Long notice_id) throws DataAccessException;

    // 한개의 이미지 글 수정(수정과 추가는 비슷함)
    //public void updateArticle(ArticleDTO articleDTO) throws DataAccessException;

    // 여러개의 이미지 "글" 수정 board tbl
    public void updateArticle(Map articleMap) throws DataAccessException;

    // 여러개의 "이미지" 수정 image tbl
    public void updateImage(Map articleMap) throws DataAccessException;

    // 글 삭제
    public void deleteArticle(Long notice_id) throws DataAccessException;

    public void deleteImage(Long notice_id) throws DataAccessException;
}
