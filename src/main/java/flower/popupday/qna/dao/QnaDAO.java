package flower.popupday.qna.dao;

import flower.popupday.qna.dto.QnaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("qnaDAO")
public interface QnaDAO {

    public List selectAllQnaList (@Param("count") int count) throws DataAccessException;

    public int selectTotalQna() throws DataAccessException;
    

    void insertNewQna(Map qnaMap);

    void updateQna(Map qnaMap);

    Long getNewUserId();

    QnaDTO selectAllQnaList(Long userId);

    void updateQna(Long userId);
}