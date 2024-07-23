package flower.popupday.search.dao;

import flower.popupday.popup.dto.PopupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper // MyBatis 매퍼 인터페이스임을 나타냄
public interface SearchDAO {
    // 해시태그로 팝업을 검색하는 메서드 선언
    // 쿼리 파라미터를 받아서 PopupDTO 리스트를 반환하며, 데이터 액세스 예외를 던질 수 있음
    List<PopupDTO> searchPopupsByHashTag(String query) throws DataAccessException;

    List<PopupDTO> searchPopupsByWord(String keyword) throws DataAccessException;
}
