package flower.popupday.notice.faq.dao;

import flower.popupday.notice.faq.dto.FaqDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.zip.DataFormatException;

@Mapper
@Repository("faqDAO")
public interface FaqDAO {

//    public FaqDTO insertFaq(FaqDTO faqDTO) throws DataFormatException;
    // 글 번호 생성
    public int getNewArticleNo() throws DataAccessException;

    // 새 글 추가 (글번호 생성 후 새 글 추가 호출) xml 에서는 Map,DTO(둘중하나)로 받고 줄때는 DTO로 (여러개 이미지)
    public void insertNewArticle(Map articleMap) throws DataAccessException;
}
