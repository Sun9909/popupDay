package flower.popupday.notice.faq.service;

import flower.popupday.notice.faq.dto.FaqDTO;

import java.util.Map;
import java.util.zip.DataFormatException;

public interface FaqService {

    public int addFaq(Map articleMap) throws DataFormatException;
}
