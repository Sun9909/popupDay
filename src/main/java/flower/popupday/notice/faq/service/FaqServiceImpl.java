package flower.popupday.notice.faq.service;

import flower.popupday.notice.faq.dao.FaqDAO;
import flower.popupday.notice.faq.dto.FaqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("faqService")
public class FaqServiceImpl implements FaqService {

    @Autowired
    private FaqDAO faqDAO;

//    @Override
//    public void addFaq(FaqDTO faqDTO) throws DataFormatException {
//        faqDAO.insertFaq(faqDTO);
//    }

    // 여러개의 이미지 추가하기
    public int addFaq(Map articleMap) throws DataAccessException {
        int articleNo=faqDAO.getNewArticleNo(); // 글번호 받아오는 메서드
        articleMap.put("articleNo",articleNo); // 얻어온 번호 주입
        faqDAO.insertNewArticle(articleMap);
        return articleNo;
    }

}
