package flower.popupday.notice.QnA.dto;

import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.time.LocalDateTime;

@Component("QnaDTO")
public class QnaDTO {
    private Long qna_id;
    private Long user_id;
    private String title;
    private String content;
    private LocalDateTime reg_date;
    private String answer;   private LocalDateTime answer_date;
    private Role status;

    // QnaDTO 선언
    public QnaDTO() {}

    // Getters and Setters
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

    public LocalDateTime getReg_date() {
        return reg_date;
    }

    public void setReg_date(LocalDateTime reg_date) {
        this.reg_date = reg_date;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDateTime getAnswer_date() {
        return answer_date;
    }

    public void setAnswer_date(LocalDateTime answer_date) {
        this.answer_date = answer_date;
    }

    public Role getStatus() {
        return status;
    }

    public void setStatus(Role status) {
        this.status = status;
    }

    public enum Status {
        승인, 미승인, 승인취소
    }

    // 모든 필드를 포함하는 생성자
    public QnaDTO(Long qna_id, Long user_id, String title, String content, LocalDateTime reg_date, String answer, LocalDateTime answer_date, Role status) {
        this.qna_id = qna_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.reg_date = reg_date;
        this.answer = answer;
        this.answer_date = answer_date;
        this.status = status;
    }

    // 값이 전달 안 될때 toString 호출해서 확인
    @Override
    public String toString() {
        return "QnaDTO{" +
                "qna_id=" + qna_id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", reg_date=" + reg_date +
                ", answer='" + answer + '\'' +
                ", answer_date=" + answer_date +
                ", status=" + status +
                '}';
    }

}
