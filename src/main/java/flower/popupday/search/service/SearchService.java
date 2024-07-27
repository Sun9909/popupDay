package flower.popupday.search.service;

import flower.popupday.popup.dto.PopupDTO;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface SearchService {
    List<PopupDTO> searchPopupHasTag(String hashTag);

    List<PopupDTO> searchPopupsByWord(String keyword);

    // 특정 날짜에 해당하는 팝업 검색
    List<PopupDTO> searchPopupsByDate(Date selectedDate);

    // 날짜 조건에 따라 팝업 상태 구분
    // 종료된 팝업
    List<PopupDTO> searchFinishedPopups(Date today);

    // 진행 중인 팝업
    List<PopupDTO> searchOngoingPopups(Date today);

    // 예정된 팝업
    List<PopupDTO> searchUpcomingPopups(Date today);

}
