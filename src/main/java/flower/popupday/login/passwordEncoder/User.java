package flower.popupday.login.passwordEncoder;

import jakarta.persistence.*;  // JPA 어노테이션을 사용하기 위해 임포트
import java.sql.Date;  // 날짜 타입을 사용하기 위해 임포트

@Entity  // 이 클래스를 JPA 엔티티로 표시
public class User {

    @Id  // 이 필드를 기본 키로 표시
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 기본 키 생성을 데이터베이스에 위임
    private Long id;
    private String userId;
    private String userNickname;
    private String name;
    private String password;
    private String email;
    private Date regDate;
    private String businessNum;

    @Enumerated(EnumType.STRING)  // Enum 값을 문자열로 저장
    private Role role;

    @Enumerated(EnumType.STRING)  // Enum 값을 문자열로 저장
    private Status status;

    public User() {}  // 기본 생성자

    public User(Long id, String userId, String userNickname, String name, String password, String email, Date regDate, String businessNum, Role role, Status status) {
        this.id = id;
        this.userId = userId;
        this.userNickname = userNickname;
        this.name = name;
        this.password = password;
        this.email = email;
        this.regDate = regDate;
        this.businessNum = businessNum;
        this.role = role;
        this.status = status;
    }

    public enum Role {  // 사용자 역할을 정의하는 열거형
        일반, 사업자, 관리자, 카카오
    }

    public enum Status {  // 사용자 상태를 정의하는 열거형
        active, delete
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(String businessNum) {
        this.businessNum = businessNum;
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
}
