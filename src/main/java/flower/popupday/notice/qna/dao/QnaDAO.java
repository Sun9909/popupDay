
package flower.popupday.notice.qna.dao;

import flower.popupday.notice.faq.dto.FaqDTO;
import flower.popupday.notice.qna.dto.QnaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

@Mapper
public interface QnaDAO {

    public void insertQna(QnaDTO qnaDTO) throws DataFormatException;

    public List selectAllQnaList(@Param("count") int count) throws DataAccessException;

    public int selectToQna() throws DataAccessException;

    public QnaDTO selectQna(long qna_id) throws DataAccessException;

    public void changeQna(QnaDTO qnaDTO) throws DataAccessException;

    public void deleteQna(long qna_id) throws DataAccessException;

    public void insertAnswer(QnaDTO qnaDTO) throws DataAccessException;

    public void selectQnaById(long qna_id) throws DataAccessException;


}
