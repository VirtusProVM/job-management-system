package com.jobentry.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "candidate")
public class Candidate implements java.io.Serializable {

    @Id
    private Integer candidateID;

    @OneToOne
    @JoinColumn(name = "candidateID")
    @MapsId
    private Users users;

    private String firstname;

    private String lastname;

    private String city;

    private String country;

    private String state;

    private String employmentType;

    private String profilePhoto;

    private String resume;

    private String workAuthorization;

    @OneToMany(targetEntity = Skills.class, cascade = CascadeType.ALL, mappedBy = "candidate")
    private List<Skills> skills;

    public Candidate() {}

    public Candidate(Users users) {
        this.users = users;
    }

    public Candidate(Integer candidateID, Users users, String firstname, String lastname, String city, String country,
                     String state, String employmentType, String profilePhoto, String resume,
                     String workAuthorization,List<Skills> skills) {
        this.candidateID = candidateID;
        this.users = users;
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;
        this.state = state;
        this.employmentType = employmentType;
        this.profilePhoto = profilePhoto;
        this.resume = resume;
        this.workAuthorization = workAuthorization;
        this.skills = skills;
    }

    public Integer getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Integer candidateID) {
        this.candidateID = candidateID;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getWorkAuthorization() {
        return workAuthorization;
    }

    public void setWorkAuthorization(String workAuthorization) {
        this.workAuthorization = workAuthorization;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    @Transient
    public String getPhotosImagePath() {
        if (profilePhoto == null || candidateID == null) return null;
        return "photos/candidate/" + firstname + " " + lastname + "/" + profilePhoto;
    }


    @Override
    public String toString() {
        return "Candidate{" +
                "candidateID=" + candidateID +
                ", users=" + users +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", resume='" + resume + '\'' +
                ", workAuthorization='" + workAuthorization + '\'' +
                ", skills=" + skills +
                '}';
    }
}
