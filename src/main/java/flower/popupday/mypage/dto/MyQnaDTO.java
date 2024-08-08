package flower.popupday.mypage.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("myqnaDTO")
public class MyQnaDTO {
    private Long qna_id;
    private Long user_id;
    private String title;
    private String content;
    private Date created_date;
    private String answer;
    private Date answer_date;
    private Status status;

    //생성자
    public MyQnaDTO() {

    }

    public enum Status {
        답변중, 답변완료
    }

    public Long getQna_id() {
        return qna_id;
    }

    public void setQna_id(Long qna_id) {
        this.qna_id = qna_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}