package flower.popupday.search.service;

import flower.popupday.popup.dto.PopupDTO;
import flower.popupday.search.dao.SearchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {


    @Autowired // 의존성 주입을 통해 SearchDAO를 주입
    private SearchDAO searchDAO;

    @Override
    public List<PopupDTO> searchPopupHasTag(String hashTag) {
        // DAO를 통해 해시태그로 팝업을 검색
        return searchDAO.searchPopupHasTag(hashTag);
    }

    @Override
    public List<PopupDTO> searchPopupsByWord(String keyword) {
        // DAO를 통해 키워드로 팝업을 검색
        return searchDAO.searchPopupsByWord(keyword);
    }

    @Override
    public List<PopupDTO> searchPopupsByDate(Date selectedDate) {
        // DAO를 통해 선택된 날짜로 팝업을 검색
        return searchDAO.searchPopupsByDate(selectedDate);
    }

    @Override
    public List<PopupDTO> searchFinishedPopups() {
        // DAO를 통해 종료된 팝업을 검색
        return searchDAO.searchFinishedPopups();
    }

    @Override
    public List<PopupDTO> searchOngoingPopups() {
        // DAO를 통해 진행 중인 팝업을 검색
        return searchDAO.searchOngoingPopups();
    }

    @Override
    public List<PopupDTO> searchUpcomingPopups() {
        // DAO를 통해 예정된 팝업을 검색
        return searchDAO.searchUpcomingPopups();
    }
}
