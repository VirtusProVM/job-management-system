package com.jobentry.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skillID;

    private String experienceLevel;

    private String name;

    private String yearsOfExperience;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidateID")
    private Candidate candidate;

    public Skills() {}

    public Skills(Integer skillID, String experienceLevel, String name, String yearsOfExperience, Candidate candidate) {
        this.skillID = skillID;
        this.experienceLevel = experienceLevel;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.candidate = candidate;
    }

    public Integer getSkillID() {
        return skillID;
    }

    public void setSkillID(Integer skillID) {
        this.skillID = skillID;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    @Override
    public String toString() {
        return "Skills{" +
                "skillID=" + skillID +
                ", experienceLevel='" + experienceLevel + '\'' +
                ", name='" + name + '\'' +
                ", yearsOfExperience='" + yearsOfExperience + '\'' +
                ", candidate=" + candidate +
                '}';
    }
}
