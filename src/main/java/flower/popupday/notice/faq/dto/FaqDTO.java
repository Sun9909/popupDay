package flower.popupday.notice.faq.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("faqDTO")
public class FaqDTO {
    private long faq_id; //faq 번호
    private long user_id; //작성자 아이디
    private String title; //faq 제목
    private String content; //faq 내용
    private Date created_date;

    private int totFaq;

    public FaqDTO() {}

    public FaqDTO(long faq_id, long user_id, String title, String content, Date created_date, int totfaq) {
        this.faq_id = faq_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.created_date = created_date;
        this.totFaq = totfaq;
    }

    public long getFaq_id() {
        return faq_id;
    }

    public void setFaq_id(long faq_id) {
        this.faq_id = faq_id;
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


    public int getTotFaq() {
        return totFaq;
    }

    public void setTotFaq(int totFaq) {
        this.totFaq = totFaq;
    }
}