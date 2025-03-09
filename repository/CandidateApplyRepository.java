package com.jobentry.repository;

import com.jobentry.entity.Candidate;
import com.jobentry.entity.CandidateApply;
import com.jobentry.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateApplyRepository extends JpaRepository<CandidateApply, Integer> {

    List<CandidateApply> findByUserId(Candidate candidate);

    List<CandidateApply> findByJob(Jobs job);
}
