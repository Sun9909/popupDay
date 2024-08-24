package flower.popupday.notice.faq.dao;

import flower.popupday.notice.faq.dto.FaqDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

@Mapper
public interface FaqDAO {

    //글 추가
    public void insertFaq(FaqDTO faqDTO) throws DataAccessException;

    // 전체 글 조회
    public List selectAllFaqList(@Param("count") int count) throws DataAccessException;

    public int selectToFaq() throws DataAccessException;

    //전체 faq 개수 조회(페이징용)
    public int selectTofaq() throws DataAccessException;

    public void changeFaq(FaqDTO faqDTO) throws DataAccessException;

    public void deleteFaq(int faq_id) throws DataAccessException;

    public int getNewFaqNo() throws DataAccessException;

}
