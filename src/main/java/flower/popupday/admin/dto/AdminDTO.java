package flower.popupday.admin.dto;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component("adminDTO")
public class AdminDTO {
    private Long id; //회원번호
    private String user_id; //유저아이디
    private String user_nickname; //유저닉네임
    private String name; //유저이름
    private String pwd; //유저비번
    private String email;   //유저 이메일
    private Date reg_date; //가입일
    private Role role;  //일반, 사업자, 관리자 구분
    private Status status;    //가입, 탈퇴
    private Date birth_date;
    private String gender;
    private Long tot_point;
    private String profile;
    private String business_num;

    //생성자
    public AdminDTO() {

    }

    public enum Role {
        일반, 사업자, 관리자
    }

    public enum Status {
        active, deleted
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

    public Long getTot_point() {
        return tot_point;
    }

    public void setTot_point(Long tot_point) {
        this.tot_point = tot_point;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBusiness_num() {
        return business_num;
    }

    public void setBusiness_num(String business_num) {
        this.business_num = business_num;
    }
}