package flower.popupday.search.dao;

import flower.popupday.popup.dto.PopupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.sql.Date;
import java.util.List;

@Mapper // 이 인터페이스가 MyBatis 매퍼임을 나타냅니다.
public interface SearchDAO {
    // 해시태그로 팝업 검색 메서드
    List<PopupDTO> searchPopupHasTag(@Param("hash_tag") String hashTag);

    // 키워드로 팝업 검색 메서드
    List<PopupDTO> searchPopupsByWord(@Param("keyword") String keyword);

    // 날짜로 팝업 검색 메서드
    List<PopupDTO> searchPopupsByDate(@Param("selectedDate") Date selectedDate);

    // 종료된 팝업 검색 메서드
    List<PopupDTO> searchFinishedPopups();

    // 진행 중인 팝업 검색 메서드
    List<PopupDTO> searchOngoingPopups();

    // 예정된 팝업 검색 메서드
    List<PopupDTO> searchUpcomingPopups();
}
