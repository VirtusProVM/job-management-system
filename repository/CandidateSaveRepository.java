package com.jobentry.repository;

import com.jobentry.entity.Candidate;
import com.jobentry.entity.CandidateSave;
import com.jobentry.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateSaveRepository extends JpaRepository<CandidateSave, Integer> {

    public List<CandidateSave> findByUserId(Candidate candidateID);

    List<CandidateSave> findByJob(Jobs jobs);
}
