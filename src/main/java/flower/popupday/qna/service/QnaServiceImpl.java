package flower.popupday.qna.service;

import flower.popupday.qna.dao.QnaDAO;
import flower.popupday.qna.dto.QnaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("qnaService")
public class QnaServiceImpl implements QnaService {

    @Qualifier("qnaDAO")
    @Autowired
    private QnaDAO qnaDAO;

    @Autowired
    private QnaDTO qnaDTO;

    @Override
    public Map<String, Object> listQna(Map<String, Integer>paginMap) throws DataAccessException {
        Map<String, Object>qnaMap = new HashMap<>();
        int section = paginMap.get("section");
        int pageNum = paginMap.get("pageNum");
        int count = (section-1)*100 + (pageNum-1) *10;
        List<QnaDTO> listQna = qnaDAO.selectAllQnaList(count);

        int totqna = qnaDAO.selectTotalQna();
        qnaMap.put("listQna", listQna);
        qnaMap.put("totqna", totqna);

        return qnaMap;
    }

    @Override
    public void updateQna(QnaDTO qna) {

    }

    @Override
    public void deleteQna(Long qnaId) {

    }

    @Override
    public void answerQna(Long qnaId, String answer) {

    }

    //상세글보기
    @Override
    public Map viewQna(Long user_id) throws DataAccessException {
        Map qnaMap = new HashMap<>();
        QnaDTO qnaDTO = qnaDAO.selectAllQnaList(user_id);

        qnaMap.put("qna",qnaDTO);

        return qnaMap;
    }

    //글추가하기
    @Override
    public void addQna(Map qnaMap) throws DataAccessException {
        Long user_id = qnaDAO.getNewUserId(); // 메서드 이름 수정: geteNewUserId() → getNewUserId()
        qnaMap.put("user_id", user_id); // qnaMap에 userId 추가
        qnaDAO.insertNewQna(qnaMap); // Q&A를 데이터베이스에 추가하는 메서드 호출
    }

    @Override
    public QnaDTO getQnaById(Long qnaId) {
        return null;
    }


    //글 수정하기
    @Override
    public void modQna(Map qnaMap) throws DataAccessException {
        qnaDAO.updateQna(qnaMap);
    }

    //글 삭제하기
    @Override
    public void removeQna(Long user_id) throws DataAccessException {
        qnaDAO.updateQna(user_id);
    }
}
