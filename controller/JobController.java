package com.jobentry.controller;

import com.jobentry.entity.*;
import com.jobentry.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class JobController {

    private final JobsService jobsService;
    private UsersService usersService;

    private CategoryService categoryService;

    private RecruiterService recruiterService;

    private CandidateService candidateService;

    private CandidateApplyService candidateApplyService;

    private CandidateSaveService candidateSaveService;

    @Autowired
    public JobController(JobsService jobsService, UsersService usersService, CategoryService categoryService,
                         RecruiterService recruiterService, CandidateService candidateService, CandidateApplyService candidateApplyService,
                         CandidateSaveService candidateSaveService) {
        this.jobsService = jobsService;
        this.usersService = usersService;
        this.categoryService = categoryService;
        this.recruiterService = recruiterService;
        this.candidateService = candidateService;
        this.candidateApplyService = candidateApplyService;
        this.candidateSaveService = candidateSaveService;
    }

    @GetMapping("/dashboard/")
    public String JobBoard(Model model) {

        Users users = usersService.getCurrentUser();
        int userID = users.getUserID();
        model.addAttribute("user", users);

        List<Jobs> jobs = jobsService.getJobsByUser(userID);

        model.addAttribute("jobs", jobs);

        return "dashboard";
    }

    @GetMapping("dashboard/jobInfo/{id}")
    public String jobInformation(@PathVariable("id") int id, Model model) {

        Jobs jobs = jobsService.getJob(id);
        List<CandidateApply> candidateApplyList = candidateApplyService.getJobCandidates(jobs);
        List<CandidateSave> candidateSaves = candidateSaveService.getJobCandidates(jobs);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                Recruiter user = recruiterService.getCurrentRecruiterProfile();
                if (user != null) {
                    model.addAttribute("applyList", candidateApplyList);
                }
            } else {
                Candidate candidate = candidateService.getCurrentCandidateProfile();
                if (candidate != null) {
                    boolean exists = false;
                    boolean saved = false;
                    for (CandidateApply candidateApply : candidateApplyList) {
                        if (candidateApply.getCandidate().getCandidateID() == candidate.getCandidateID()) {
                            exists = true;
                            break;
                        }
                    }
                    for (CandidateSave candidateSave : candidateSaves) {
                        if (candidateSave.getCandidate().getCandidateID() == candidate.getCandidateID()) {
                            saved = true;
                            break;
                        }
                    }
                    model.addAttribute("alreadyApplied", exists);
                    model.addAttribute("alreadySaved", saved);
                }
            }
        }

        CandidateApply candidateApply = new CandidateApply();
        model.addAttribute("applyJob", candidateApply);

        model.addAttribute("jobDetails", jobs);
        model.addAttribute("user", usersService.getCurrentUserProfile());

        return "candidate-apply";
    }

    @GetMapping("/jobInfo/{id}")
    public String viewJob(@PathVariable("id") int id, Model model) {

        Jobs jobs = jobsService.getJob(id);
        List<CandidateApply> candidateApplyList = candidateApplyService.getJobCandidates(jobs);
        List<CandidateSave> candidateSaves = candidateSaveService.getJobCandidates(jobs);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                Recruiter user = recruiterService.getCurrentRecruiterProfile();
                if (user != null) {
                    model.addAttribute("applyList", candidateApplyList);
                }
            } else {
                Candidate candidate = candidateService.getCurrentCandidateProfile();
                if (candidate != null) {
                    boolean exists = false;
                    boolean saved = false;
                    for (CandidateApply candidateApply : candidateApplyList) {
                        if (candidateApply.getCandidate().getCandidateID() == candidate.getCandidateID()) {
                            exists = true;
                            break;
                        }
                    }
                    for (CandidateSave candidateSave : candidateSaves) {
                        if (candidateSave.getCandidate().getCandidateID() == candidate.getCandidateID()) {
                            saved = true;
                            break;
                        }
                    }
                    model.addAttribute("alreadyApplied", exists);
                    model.addAttribute("alreadySaved", saved);
                }
            }
        }

        CandidateApply candidateApply = new CandidateApply();
        model.addAttribute("applyJob", candidateApply);

        model.addAttribute("jobDetails", jobs);
        model.addAttribute("user", usersService.getCurrentUserProfile());

        return "job-detail";
    }

    @PostMapping("search-results/")
    public String search(Model model,
                         @RequestParam("job") String job,
                         @RequestParam("category") String category,
                         @RequestParam("location") String location) {

        List<Jobs> jobs = null;

        if(!StringUtils.hasText(job) && !StringUtils.hasText(category) && !StringUtils.hasText(location)) {
            jobs = jobsService.getAll();
        } else {
            jobs = jobsService.search(job, category, location);
        }

        model.addAttribute("jobs", jobs);

        return "job-list";
    }

    @GetMapping("/showAllJobs")
    public String getAllJobs(Model model) {

        List<Jobs> jobs = jobsService.showFullJobs();

        model.addAttribute("jobs", jobs);

        return "job-list";
    }

    @GetMapping("/dashboard/add")
    public String addJobs(Model model) {

        List<Category> categories = categoryService.getAll();

        model.addAttribute("categories", categories);

        model.addAttribute("job", new Jobs());
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-new-job";
    }


    @PostMapping("/dashboard/addNewJob")
    public String addNew(@ModelAttribute("job") Jobs jobs, @RequestParam(value = "category", required = false) Integer id, Model model) {

        Category category;

        if(id != null) {
            category = categoryService.getSelectedCategory(id);
        } else {
            category = new Category();
        }

        Users user = usersService.getCurrentUser();
        if (user != null) {
            jobs.setPostedByID(user);
        }

        jobs.setPostedDate(new Date());

        model.addAttribute("job", jobs);
        jobsService.addNew(jobs);
        categoryService.saveCategory(category);
        return "redirect:/dashboard/";
    }

    @GetMapping("dashboard/editJob/{id}")
    public String editJob(@PathVariable("id") int id, Model model) {

        List<Category> categories = categoryService.getAll();

        model.addAttribute("categories", categories);

        Jobs job = jobsService.getJob(id);
        model.addAttribute("job", job);
        model.addAttribute("user", usersService.getCurrentUserProfile());

        return "edit-job-form";
    }

    @PostMapping("dashboard/updateJob/{id}")
    public String updateJob(@PathVariable("id") int id, Model model, @ModelAttribute("job") Jobs job, BindingResult result) {

        Category category = job.getCategory();

        if(category != null) {
            job.setCategory(category);
        }

        Users user = usersService.getCurrentUser();
        if (user != null) {
            job.setPostedByID(user);
        }

        if(result.hasErrors()) {
            job.setJobsID(id);

            return "edit-job-form";

        }

        job.setPostedDate(new Date());

        jobsService.addNew(job);

        return "redirect:/dashboard/";
    }
}
