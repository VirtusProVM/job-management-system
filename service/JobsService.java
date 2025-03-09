package com.jobentry.service;

import com.jobentry.entity.Category;
import com.jobentry.entity.Jobs;
import com.jobentry.repository.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobsService {

    private JobsRepository jobsRepository;

    @Autowired
    public JobsService(JobsRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    public List<Jobs> getAll() {
        return jobsRepository.getAllJobs();
    }

    public List<Jobs> showFullJobs() {
        return jobsRepository.getAll();
    }

    public List<Jobs> getFulltimeJobs() {
        return jobsRepository.getFullTimeJobs();
    }

    public List<Jobs> getParttimeJobs() {
        return jobsRepository.getParttimeJobs();
    }

    public List<Jobs> getRemoteJobs() {
        return jobsRepository.getRemote();
    }

    public List<Jobs> getJobsByCategory(int id) {

        return jobsRepository.getJobsByCategory(id);
    }

    public Jobs getJobDetails(int id) {

        Jobs job = jobsRepository.getJobDetails(id);

        return job;
    }

    public List<Jobs> search(String job, String category, String location) {
        return jobsRepository.searchJobs(job, category, location);
    }

    public Jobs addNew(Jobs jobs) {
        return jobsRepository.save(jobs);
    }

    public List<Jobs> getJobsByUser(int userID) {
        return jobsRepository.getJobsByUser(userID);
    }

    public Jobs getJob(int id) {
        return jobsRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found!!!"));
    }

    public void delete(int id) {

        jobsRepository.deleteById(id);
    }
}
