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
        int faq_id = faqDAO.getNewFaqNo();
        faqDTO.setFaq_id(faq_id);
        faqDAO.insertFaq(faqDTO);

    }

    @Override
    public List listFaq(int section, int pageNum) throws DataAccessException {

        int count=(section-1)*100+(pageNum-1)*10;
        List<FaqDTO> faqList=faqDAO.selectAllFaqList(count);

        int totFaq=faqDAO.selectTofaq();
        for (FaqDTO tfaq: faqList){
            tfaq.setTotFaq(totFaq);
        }

        return faqList;
    }

    @Override
    public void modFaq(FaqDTO faqDTO) throws DataFormatException {
        faqDAO.changeFaq(faqDTO);
    }

    @Override
    public void removeFaq(int faq_id) throws DataAccessException {
        faqDAO.deleteFaq(faq_id);
    }


}