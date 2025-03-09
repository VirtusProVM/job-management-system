package com.jobentry.service;

import com.jobentry.entity.Candidate;
import com.jobentry.entity.CandidateApply;
import com.jobentry.entity.Jobs;
import com.jobentry.repository.CandidateApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateApplyService {

    private final CandidateApplyRepository candidateApplyRepository;

    @Autowired
    public CandidateApplyService(CandidateApplyRepository candidateApplyRepository) {
        this.candidateApplyRepository = candidateApplyRepository;
    }

    public List<CandidateApply> getCandidatesJobs(Candidate candidate) {
        return candidateApplyRepository.findByUserId(candidate);
    }

    public List<CandidateApply> getJobCandidates(Jobs job) {
        return candidateApplyRepository.findByJob(job);
    }

    public void addNew(CandidateApply candidateApply) {
        candidateApplyRepository.save(candidateApply);
    }
}
