package com.jobentry.service;

import com.jobentry.entity.Candidate;
import com.jobentry.entity.CandidateSave;
import com.jobentry.entity.Jobs;
import com.jobentry.repository.CandidateSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateSaveService {

    private CandidateSaveRepository candidateSaveRepository;

    @Autowired
    public CandidateSaveService(CandidateSaveRepository candidateSaveRepository) {
        this.candidateSaveRepository = candidateSaveRepository;
    }

    public List<CandidateSave> getCandidatesJob(Candidate candidate) {
        return candidateSaveRepository.findByUserId(candidate);
    }

    public List<CandidateSave> getJobCandidates(Jobs job) {
        return candidateSaveRepository.findByJob(job);
    }

    public void addNew(CandidateSave candidateSave) {
        candidateSaveRepository.save(candidateSave);

    }
}
