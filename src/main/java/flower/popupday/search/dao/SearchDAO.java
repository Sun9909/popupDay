package flower.popupday.search.dao;

import flower.popupday.popup.dto.PopupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

@Mapper
public interface SearchDAO {
    List<PopupDTO> searchPopupHasTag(@Param("hash_tag") String hashTag);

    List<PopupDTO> searchPopupsByWord(@Param("keyword") String keyword);

    List<PopupDTO> searchPopupsByDate(@Param("selectedDate") Date selectedDate);

    // 종료된 팝업
    List<PopupDTO> searchFinishedPopups(@Param("today") Date today);

    // 진행 중인 팝업
    List<PopupDTO> searchOngoingPopups(@Param("today") Date today);

    // 예정된 팝업
    List<PopupDTO> searchUpcomingPopups(@Param("today") Date today);

}
