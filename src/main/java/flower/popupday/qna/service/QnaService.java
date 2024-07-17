package flower.popupday.qna.service;

import flower.popupday.qna.dto.QnaDTO;
import org.springframework.dao.DataAccessException;

import java.util.Map;

public interface QnaService {

    //상세글보기
    Map viewQna(Long user_id) throws DataAccessException;

    public void addQna(Map qnaMap) throws DataAccessException;
    QnaDTO getQnaById(Long qnaId);
    public Map listQna(Map<String, Integer> pagingMap) throws DataAccessException;
    void updateQna(QnaDTO qna);
    void deleteQna(Long qnaId);
    void answerQna(Long qnaId, String answer);

    //글 수정하기
    void modQna(Map qnaMap) throws DataAccessException;

    //글 삭제하기
    void removeQna(Long user_id) throws DataAccessException;
}
