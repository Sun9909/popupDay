package flower.popupday.notice.qna.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("qnaDTO")
public class QnaDTO {

    private long qna_id;
    private long user_id;
    private String title;
    private String content;
    private String category_name;
    private Date created_at;
    private Date updated_at;
    private String status;
    private QnaAnswerDTO qnaAnswerDTO;

    //생성자
    public QnaDTO() {
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public enum Status {
        답변중, 답변완료, 답변대기
    }

    private int totQna;

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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Date getcreated_at() {
        return created_at;
    }

    public void setcreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
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
    public QnaAnswerDTO getQnaAnswerDTO() {
        return qnaAnswerDTO;
    }
    public void setQnaAnswerDTO(QnaAnswerDTO qnaAnswerDTO) {
        this.qnaAnswerDTO = qnaAnswerDTO;
    }

    public QnaDTO(long qna_id, long user_id, String title, String content, Date created_at, Date updated_at, String status, String category_name, int totQna) {
        this.qna_id= qna_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status.valueOf(status);
        this.category_name = category_name;
    }


}