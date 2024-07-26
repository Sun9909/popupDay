package flower.popupday.login.dto;

import org.springframework.stereotype.Component;
import java.sql.Date;

@Component("loginDTO")
public class LoginDTO {

    private Long id;
    private String user_id;
    private String user_nickname;
    private String name;
    private String pwd;
    private String email;
    private Date reg_date;
    private String business_num;
    private Role role;
    private Status status;

    public LoginDTO() {
    }

    public enum Role {
        일반, 사업자, 관리자, 카카오
    }

    public enum Status {
        active, deleted
    }

    public LoginDTO(Long id, String user_id, String name, String pwd, String email, Date reg_date, String business_num, String role, String status) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.reg_date = reg_date;
        this.business_num = business_num;  // 수정된 부분
        this.role = Role.valueOf(role);
        this.status = Status.valueOf(status);
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

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
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

    public String getBusiness_num() {  // 수정된 부분
        return business_num;
    }

    public void setBusiness_num(String business_num) {  // 수정된 부분
        this.business_num = business_num;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // isAdmin() 메서드 추가
    public boolean isAdmin() {return this.role == Role.관리자;
    }
}
