package flower.popupday.notice.qna.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("qnaDTO")
public class QnaDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long qna_id;
    private long user_id;
    private String title;
    private String content;
    private Date created_date;
    private String answer;
    private Date answer_date;
    private String status;

    private int totQna;

    //생성자
    public QnaDTO() {
    }

    public enum Status {
        답변중, 답변완료
    }

    public long getQna_id() {
        return qna_id;
    }

    public void setQna_id(long qna_id) {
        this.qna_id = qna_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAnswer_date() {
        return answer_date;
    }

    public void setAnswer_date(Date answer_date) {
        this.answer_date = answer_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotQna() {
        return totQna;
    }

    public void setTotQna(int totQna) {
        this.totQna = totQna;
    }

    public QnaDTO(long qna_id, long user_id, String title, String content, Date created_date, String answer, Date answer_date, String status, int totQna) {
        this.qna_id= qna_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.created_date = created_date;
        this.answer = answer;
        this.answer_date = answer_date;
        this.status = status.valueOf(status);
    }


}