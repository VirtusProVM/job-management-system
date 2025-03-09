package com.jobentry.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate_save", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "job"})
})
public class CandidateSave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "candidateID")
    private Candidate userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "jobsID")
    private Jobs job;

    public CandidateSave() {}

    public CandidateSave(Integer id, Candidate userId, Jobs job) {
        this.id = id;
        this.userId = userId;
        this.job = job;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return userId;
    }

    public void setCandidate(Candidate userId) {
        this.userId = userId;
    }

    public Jobs getJobs() {
        return job;
    }

    public void setJobs(Jobs job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "CandidateSave{" +
                "id=" + id +
                ", userId=" + userId +
                ", job=" + job +
                '}';
    }
}
