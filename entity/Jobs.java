package com.jobentry.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "jobs")
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobsID;

    private String jobType;

    private String jobTitle;

    private String jobDescription;

    private Date postedDate;

    private String location;

    private String company;

    private String salary;

    @Transient
    private Boolean isActive;

    @Transient
    private Boolean isSaved;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryID", referencedColumnName = "categoryID")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "posted_by_id", referencedColumnName = "userID")
    private Users postedByID;

    public Jobs() {}

    public Jobs(Integer jobsID, String jobType, String jobTitle, String jobDescription, Date postedDate, String location,
                String company, String salary, Boolean isActive, Boolean isSaved, Category category, Users postedByID) {
        this.jobsID = jobsID;
        this.jobType = jobType;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.postedDate = postedDate;
        this.location = location;
        this.company = company;
        this.salary = salary;
        this.isActive = isActive;
        this.isSaved = isSaved;
        this.category = category;
        this.postedByID = postedByID;
    }

    public Integer getJobsID() {
        return jobsID;
    }

    public void setJobsID(Integer jobsID) {
        this.jobsID = jobsID;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(Boolean isSaved) {
        this.isSaved = isSaved;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Users getPostedByID() {
        return postedByID;
    }

    public void setPostedByID(Users postedByID) {
        this.postedByID = postedByID;
    }

    @Override
    public String toString() {
        return "Jobs{" +
                "jobsID=" + jobsID +
                ", jobType='" + jobType + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", postedDate=" + postedDate +
                ", location='" + location + '\'' +
                ", company='" + company + '\'' +
                ", salary='" + salary + '\'' +
                ", isActive=" + isActive +
                ", isSaved=" + isSaved +
                ", category=" + category +
                ", postedByID=" + postedByID +
                '}';
    }
}
