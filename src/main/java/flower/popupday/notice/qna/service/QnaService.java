package flower.popupday.notice.qna.service;

import flower.popupday.notice.qna.dto.QnaDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

public interface QnaService {

    public void addQna(QnaDTO qnaDTO) throws DataFormatException;

    public Map listQna(Map<String, Integer> pagingMap) throws DataAccessException;

    public void modQna(QnaDTO qnaDTO) throws DataFormatException;

    public  void removeQna(long qna_id) throws DataAccessException;

    public Map qnaView(long qna_id) throws DataAccessException;

    public void addAnswer(QnaDTO qnaDTO) throws DataFormatException;

    public QnaDTO getQnaById(long qna_id) throws DataAccessException;

}