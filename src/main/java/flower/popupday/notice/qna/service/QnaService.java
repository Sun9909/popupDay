package flower.popupday.notice.qna.service;

import flower.popupday.notice.faq.dto.FaqDTO;
import flower.popupday.notice.qna.dto.QnaDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.zip.DataFormatException;

public interface QnaService {

    public void addQna(QnaDTO qnaDTO) throws DataFormatException;

    public List listQna(int section, int pageNum) throws DataAccessException;

    public void modQna(QnaDTO qnaDTO) throws DataFormatException;

    public  void removeQna(int qna_id) throws DataAccessException;
}
