package flower.popupday.notice.faq.dao;

import flower.popupday.notice.faq.dto.FaqDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

@Mapper
public interface FaqDAO {

    public void insertFaq(FaqDTO faqDTO) throws DataAccessException;

    public List selectAllFaqList() throws DataAccessException;

    public int selectToFaq() throws DataAccessException;
}
