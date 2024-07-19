package flower.popupday.admin.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("adminDTO")
public class AdminDTO {
    private Long id; //회원번호
    private String user_id; //유저아이디
    private String user_nikname; //유저닉네임
    private String name; //유저이름
    private String pwd; //유저비번
    private String email;   //유저 이메일
    private Date reg_date; //가입일
    private Role role;  //일반, 사업자, 관리자 구분
    private Status status;    //가입, 탈퇴

    //생성자
    public AdminDTO() {

    }

    public enum Role {
        일반, 사업자, 관리자
    }

    public enum Status {
        active, delete
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_nikname() {
        return user_nikname;
    }

    public void setUser_nikname(String user_nikname) {
        this.user_nikname = user_nikname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}