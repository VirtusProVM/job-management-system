package com.jobentry.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "users")
public class Users implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(unique = true)
    private String email;

    private boolean isActive;

    @NotEmpty
    private String password;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date registerDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userTypeID", referencedColumnName = "userTypeID")
    private UsersType usersType;

    public Users() {}

    public Users(Integer userID, String email, boolean isActive, String password, Date registerDate, UsersType usersType) {
        this.userID = userID;
        this.email = email;
        this.isActive = isActive;
        this.password = password;
        this.registerDate = registerDate;
        this.usersType = usersType;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public UsersType getUsersType() {
        return usersType;
    }

    public void setUsersType(UsersType usersType) {
        this.usersType = usersType;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userID=" + userID +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                ", registerDate=" + registerDate +
                ", usersType=" + usersType +
                '}';
    }
}
