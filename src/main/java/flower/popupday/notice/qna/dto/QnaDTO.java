package flower.popupday.notice.qna.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("qnaDTO")
public class QnaDTO {

        private Long qna_Id;
        private Long user_Id;
        private String title;
        private String content;
        private Date created_Date;
        private String answer;
        private Date answer_Date;
        private String status;

        //생성자
        public QnaDTO() {
        }

        public enum Status {
            답변중, 답변완료
        }

    public QnaDTO(Long qna_id, Long user_id, String title, String content, Date created_date, String answer, Date answer_date, String status) {
        this.qna_Id= qna_id;
        this.user_Id = user_id;
        this.title = title;
        this.content = content;
        this.created_Date = created_date;
        this.answer = answer;
        this.answer_Date = answer_date;
        this.status = status.valueOf(status);
    }


    public Long getQna_Id() {
        return qna_Id;
    }

    public void setQna_Id(Long qna_Id) {
        this.qna_Id = qna_Id;
    }

    public Long getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(Long user_Id) {
        this.user_Id = user_Id;
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

    public Date getCreated_Date() {
        return created_Date;
    }

    public void setCreated_Date(Date created_Date) {
        this.created_Date = created_Date;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAnswer_Date() {
        return answer_Date;
    }

    public void setAnswer_Date(Date answer_Date) {
        this.answer_Date = answer_Date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    }
