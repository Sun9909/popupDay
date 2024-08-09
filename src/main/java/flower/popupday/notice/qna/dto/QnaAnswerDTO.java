package flower.popupday.notice.qna.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Component("qnaAnswerDTO")
public class QnaAnswerDTO {

    private long answer_id;
    private long user_id;
    private long qna_id;
    private String answer;
    private Date created_at;
    private Date updated_at;

    //생성자
    public QnaAnswerDTO() {}

    public long getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(long answer_id) {
        this.answer_id = answer_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getQna_id() {
        return qna_id;
    }

    public void setQna_id(long qna_id) {
        this.qna_id = qna_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public QnaAnswerDTO(long answer_id, long user_id, long qna_id, String answer, Date created_at, Date updated_at) {
        this.answer_id=answer_id;
        this.user_id=user_id;
        this.qna_id=qna_id;
        this.answer=answer;
        this.created_at=created_at;
        this.updated_at=updated_at;
    }


}

