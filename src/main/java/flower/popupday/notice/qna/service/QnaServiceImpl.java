package flower.popupday.notice.qna.service;

import flower.popupday.notice.qna.dao.QnaDAO;
import flower.popupday.notice.qna.dto.QnaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.zip.DataFormatException;

@Service("qnaService")
public class QnaServiceImpl implements QnaService {

    @Autowired
    private QnaDAO qnaDAO;

    @Override
    public void addQna(QnaDTO qnaDTO) throws DataFormatException {
        qnaDAO.insertQna(qnaDTO);
    }

    @Override
    public List listQna(int section, int pageNum) throws DataAccessException {
        int count = (section - 1 ) * 100 + (pageNum - 1) * 10; // 현재 페이지의 시작 인텍스를 계산
        List<QnaDTO> qnaList = qnaDAO.selectAllQnaList(count); // QnaDAO를 통해 해당 인덱스부터 10개의 QnaDTO 객체 리스트를 가져옴

        int totQna = qnaDAO.selectToQna(); // 전체 Qna의 총 개수를 가져옴

        // 가져온 각각의 QnaDTO 객체에 전체 Q&A 개수를 설정
        for (QnaDTO tqna: qnaList){
            tqna.setTotQna(totQna);
        }
        return qnaList;
    }

    @Override
    public void modQna(QnaDTO qnaDTO) throws DataFormatException {
        qnaDAO.changeQna(qnaDTO);
    }

    @Override
    public void removeQna(int qna_id) throws DataAccessException {
        qnaDAO.deleteQna(qna_id);
    }


}
