package com.jobentry.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recruiter")
public class Recruiter implements java.io.Serializable {

    @Id
    private Integer recruiterID;

    @OneToOne
    @JoinColumn(name = "recruiterID")
    @MapsId
    private Users userID;

    private String city;

    private String firstname;

    private String lastname;

    private String state;

    private String country;

    private String company;

    public Recruiter() {}

    public Recruiter(Users userID) {
        this.userID = userID;
    }

    public Recruiter(Integer recruiterID, Users userID, String city, String firstname, String lastname, String state,
                     String country, String company) {
        this.recruiterID = recruiterID;
        this.userID = userID;
        this.city = city;
        this.firstname = firstname;
        this.lastname = lastname;
        this.state = state;
        this.country = country;
        this.company = company;
    }

    public Integer getRecruiterID() {
        return recruiterID;
    }

    public void setRecruiterID(Integer recruiterID) {
        this.recruiterID = recruiterID;
    }

    public Users getUsersID() {
        return userID;
    }

    public void setUsersID(Users userID) {
        this.userID = userID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Recruiter{" +
                "recruiterID=" + recruiterID +
                ", userID=" + userID +
                ", city='" + city + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
