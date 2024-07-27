package flower.popupday.search.service;

import flower.popupday.popup.dto.PopupDTO;
import java.sql.Date;
import java.util.List;

public interface SearchService {

    // 해시태그로 팝업 검색
    List<PopupDTO> searchPopupHasTag(String hashTag);

    // 키워드로 팝업 검색
    List<PopupDTO> searchPopupsByWord(String keyword);

    // 특정 날짜에 해당하는 팝업 검색
    List<PopupDTO> searchPopupsByDate(Date selectedDate);

    // 종료된 팝업 검색 (특정 날짜 이전에 종료된 팝업)
    List<PopupDTO> searchFinishedPopups();

    // 진행 중인 팝업 검색 (특정 날짜를 포함한 기간 동안 진행 중인 팝업)
    List<PopupDTO> searchOngoingPopups();

    // 예정된 팝업 검색 (특정 날짜 이후에 시작하는 팝업)
    List<PopupDTO> searchUpcomingPopups();

}
