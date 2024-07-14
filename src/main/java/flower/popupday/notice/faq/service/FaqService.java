package flower.popupday.notice.faq.service;

import flower.popupday.notice.faq.dto.FaqDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

public interface FaqService {

    public void addFaq(FaqDTO faqDTO) throws DataFormatException;

    public List listFaq(int section, int pageNum) throws DataAccessException;
}