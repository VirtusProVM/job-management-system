package com.jobentry.controller;

import com.jobentry.entity.Candidate;
import com.jobentry.entity.CandidateSave;
import com.jobentry.entity.Jobs;
import com.jobentry.entity.Users;
import com.jobentry.service.CandidateSaveService;
import com.jobentry.service.CandidateService;
import com.jobentry.service.JobsService;
import com.jobentry.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CandidateSaveController {

    private final UsersService usersService;

    private final CandidateSaveService candidateSaveService;

    private final CandidateService candidateService;

    private final JobsService jobsService;

    @Autowired
    public CandidateSaveController(UsersService usersService, CandidateSaveService candidateSaveService, CandidateService candidateService, JobsService jobsService) {
        this.usersService = usersService;
        this.candidateSaveService = candidateSaveService;
        this.candidateService = candidateService;
        this.jobsService = jobsService;
    }

    @PostMapping("job-details/save/{id}")
    public String saves(@PathVariable("id") int id, CandidateSave candidateSave) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.getUserByEmail(currentUsername);
            Optional<Candidate> seekerProfile = candidateService.getOne(user.getUserID());
            Jobs jobs = jobsService.getJob(id);
            if (seekerProfile.isPresent() && jobs != null) {
                candidateSave.setJobs(jobs);
                candidateSave.setCandidate(seekerProfile.get());
            } else {
                throw new RuntimeException("User not found");
            }
            candidateSaveService.addNew(candidateSave);
        }
        return "redirect:/dashboard/";
    }

    @GetMapping("saved-jobs/")
    public String savedJobs(Model model) {

        List<Jobs> jobsList = new ArrayList<>();
        Object currentUserProfile = usersService.getCurrentUserProfile();

        List<CandidateSave> candidateSaveList = candidateSaveService.getCandidatesJob((Candidate) currentUserProfile);
        for (CandidateSave candidateSave : candidateSaveList) {
            jobsList.add(candidateSave.getJobs());
        }

        model.addAttribute("jobList", jobsList);
        model.addAttribute("user", currentUserProfile);

        return "saved-jobs";
    }
}
