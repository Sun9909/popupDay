
package flower.popupday.notice.qna.dao;

import flower.popupday.notice.qna.dto.QnaAnswerDTO;
import flower.popupday.notice.qna.dto.QnaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.zip.DataFormatException;

@Mapper
public interface QnaDAO {

    public void insertQna(QnaDTO qnaDTO) throws DataFormatException;

    public List selectAllQnaList(@Param("count") int count) throws DataAccessException;

    public int selectToQna() throws DataAccessException;

    public QnaDTO selectQna(long qna_id) throws DataAccessException;

    public void changeQna(QnaDTO qnaDTO) throws DataAccessException;

    public void deleteQna(long qna_id) throws DataAccessException;

    public void insertAnswer(QnaAnswerDTO qnaAnswerDTO) throws DataAccessException;

    public void updateAnswer(QnaAnswerDTO qnaAnswerDTO) throws DataAccessException;

    public QnaDTO selectQnaById(long qna_id) throws DataAccessException;

    public void deleteAnswer(long qna_id) throws DataAccessException;

    public QnaAnswerDTO selectAnswerByQnaId(long qna_id) throws DataAccessException;
}