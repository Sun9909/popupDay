package flower.popupday.notice.qna.dao;

import flower.popupday.notice.faq.dto.FaqDTO;
import flower.popupday.notice.qna.dto.QnaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface QnaDAO {

    public void insertQna(QnaDTO qnaDTO) throws DataAccessException;

    public List selectAllQnaList(@Param("count") int count) throws DataAccessException;

    public int selectToQna() throws DataAccessException;

    public void changeQna(QnaDTO qnaDTO) throws DataAccessException;

    public void deleteQna(int qna_id) throws DataAccessException;


}