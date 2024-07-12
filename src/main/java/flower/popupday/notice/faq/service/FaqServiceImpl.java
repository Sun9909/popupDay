package flower.popupday.notice.faq.service;

import flower.popupday.notice.faq.dao.FaqDAO;
import flower.popupday.notice.faq.dto.FaqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

@Service("faqService")
public class FaqServiceImpl implements FaqService {

    @Autowired
    private FaqDAO faqDAO;


    @Override
    public void addFaq(FaqDTO faqDTO) throws DataFormatException {
        faqDAO.insertFaq(faqDTO);
    }

    @Override
    public List listFaq() throws DataAccessException {
        List faqList=faqDAO.selectAllFaqList();
        return faqList;
    }


}
