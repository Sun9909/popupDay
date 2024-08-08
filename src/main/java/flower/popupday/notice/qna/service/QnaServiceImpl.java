package flower.popupday.notice.qna.service;

import flower.popupday.notice.faq.dto.FaqDTO;
import flower.popupday.notice.notice.dto.NoticeDTO;
import flower.popupday.notice.qna.dao.QnaDAO;
import flower.popupday.notice.qna.dto.QnaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

@Service("qnaService")
public class QnaServiceImpl implements QnaService {

    @Autowired
    private QnaDAO qnaDAO;

    @Override
    public void addQna(QnaDTO qnaDTO) throws DataFormatException {
            qnaDAO.insertQna(qnaDTO);
    }

    @Override
    public Map<String, Object> listQna(Map<String, Integer> pagingMap) throws DataAccessException {
        Map<String, Object> qnaMap = new HashMap<>(); // 공지사항 목록과 관련 데이터를 저장할 맵을 생성

        int section = pagingMap.get("section"); // pagingMap에서 section 값을 가져와 section 변수에 저장
        int pageNum = pagingMap.get("pageNum"); // pagingMap에서 pageNum 값을 가져와 pageNum 변수에 저장

        int count = (section -1) * 100 + (pageNum - 1) * 10; // section,pageNum을 사용해 DB쿼리의 시작 위치를 계산 -> section은 100개의 공지사항을 pageNum은 10개의 공지사항을 나타냄.

        List<QnaDTO> listQna = qnaDAO.selectAllQnaList(count); //noticeDAO를 사용해 count 위치부터 공지사항 목록을 가져옴 -> NoticeDTO 객체의 리스트로 반환 됨 (전체 글 조회)
        int totQna = qnaDAO.selectToQna(); // noticeDAO를 사용해 전체 공지사항 수를 가져옴.

        qnaMap.put("listQna", listQna); // noticeList를 noticeMap에 추가
        qnaMap.put("totQna", totQna);   // totNotice를 noticeMap에 추가
        return qnaMap;  // 공지사항 목록과 전체 공지사항 수를 포함한 noticeMap으로 반환
    }

    // 공지사항 상세보기
    @Override
    public Map qnaView(long qna_id) throws DataAccessException {
        Map qnaMap = new HashMap();
        QnaDTO qnaDTO = qnaDAO.selectQna(qna_id); // noticeDAO를 사용해 notice_id에 해당하는 공지사항 정보를 가져옴
        qnaMap.put("qna", qnaDTO);
        return qnaMap;
    }

    @Override
    public void modQna(QnaDTO qnaDTO) throws DataFormatException {
        qnaDAO.changeQna(qnaDTO);
    }

    @Override
    public void removeQna(long qna_id) throws DataAccessException {
        qnaDAO.deleteQna(qna_id);
    }
    @Override
    public void addAnswer(QnaDTO qnaDTO) throws DataAccessException {
        qnaDAO.updateAnswer(qnaDTO);
    }

    @Override
    public QnaDTO getQnaById(long qna_id) throws DataAccessException {
        qnaDAO.selectQnaById(qna_id);
        return null;
    }

    //답변 수정
    @Override
    public void modAnswer(QnaDTO qnaDTO) throws DataAccessException {
        qnaDAO.updateAnswer(qnaDTO);
    }

    // 답변 삭제 메서드 추가
    public void removeAnswer(long qna_id) throws DataAccessException {
        qnaDAO.deleteAnswer(qna_id);
    }
}