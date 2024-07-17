package flower.popupday.faq.service;

import flower.popupday.faq.dto.FaqDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.zip.DataFormatException;

public interface FaqService {

    public void addFaq(FaqDTO faqDTO) throws DataFormatException;

    public List listFaq(int section, int pageNum) throws DataAccessException;

    public void modFaq(FaqDTO faqDTO) throws DataFormatException;

    public void removeFaq(int faq_id) throws DataAccessException;
}