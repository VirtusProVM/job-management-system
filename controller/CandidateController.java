package com.jobentry.controller;

import com.jobentry.entity.Candidate;
import com.jobentry.entity.Skills;
import com.jobentry.entity.Users;
import com.jobentry.filemanagement.FileDownloadManagement;
import com.jobentry.filemanagement.FileUploadManagement;
import com.jobentry.repository.UsersRepository;
import com.jobentry.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    private CandidateService candidateService;

    private UsersRepository usersRepository;

    @Autowired
    public CandidateController(CandidateService candidateService, UsersRepository usersRepository) {
        this.candidateService = candidateService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/profile")
    public String profile(Model model) {

        Candidate candidate = new Candidate();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<Skills> skills = new ArrayList<>();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() ->
                    new UsernameNotFoundException("Candidate not found"));
            Optional<Candidate> optional = candidateService.getOne(user.getUserID());

            if(optional.isPresent()) {
                candidate = optional.get();
                if(candidate.getSkills().isEmpty()) {
                    skills.add(new Skills());
                    candidate.setSkills(skills);
                }
            }

            model.addAttribute("skills", skills);
            model.addAttribute("candidate", candidate);
        }

        return "candidate-profile";
    }

    @GetMapping("/")
    public String candidateProfile(Model model) {

        Candidate candidate = new Candidate();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<Skills> skills = new ArrayList<>();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() ->
                    new UsernameNotFoundException("Candidate not found"));
            Optional<Candidate> optional = candidateService.getOne(user.getUserID());

            if(optional.isPresent()) {
                candidate = optional.get();
                if(candidate.getSkills().isEmpty()) {
                    skills.add(new Skills());
                    candidate.setSkills(skills);
                }
            }

            model.addAttribute("skills", skills);
            model.addAttribute("candidate", candidate);
        }

        return "edit-candidate-profile";
    }

    @PostMapping("/addNew")
    public String addNewCandidate(Candidate candidate, @RequestParam("image") MultipartFile image, @RequestParam("pdf") MultipartFile pdf, Model model) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() ->
                    new UsernameNotFoundException("Candidate not found"));

            candidate.setUsers(user);
            candidate.setCandidateID(user.getUserID());
        }

        List<Skills> skills = new ArrayList<>();
        model.addAttribute("skills", skills);
        model.addAttribute("candidate", candidate);

        for(Skills skill : candidate.getSkills()) {
            skill.setCandidate(candidate);
        }

        String resume = "";
        String imageFile = "";

        if(!Objects.equals(pdf.getOriginalFilename(), "")) {
            resume = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
            candidate.setResume(resume);
        }

        if(!Objects.equals(image.getOriginalFilename(), "")) {
            imageFile = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            candidate.setProfilePhoto(imageFile);
        }

        Candidate c = candidateService.addNew(candidate);

        try {
            String uploadImageDir = "photos/candidate/" + candidate.getFirstname() + " " + candidate.getLastname();
            String uploadResumeDir = "resume/candidate/" + candidate.getFirstname() + " " + candidate.getLastname();

            if(!Objects.equals(pdf.getOriginalFilename(), "")) {
                FileUploadManagement.saveFile(uploadResumeDir, resume, pdf);
            }

            if(!Objects.equals(image.getOriginalFilename(), "")) {
                FileUploadManagement.saveFile(uploadImageDir, imageFile, image);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "redirect:/dashboard/";
    }

    @GetMapping("/{id}")
    public String candidateProfilePage(@PathVariable("id") int id, Model model) {

        Optional<Candidate> candidate = candidateService.getOne(id);
        model.addAttribute("candidate", candidate.get());

        return "candidate-profile";
    }

    @GetMapping("/downloadResume")
    public ResponseEntity<?> downloadResume(@RequestParam(value = "fileName") String fileName, @RequestParam(value = "userID") String userId) {

        FileDownloadManagement downloadUtil = new FileDownloadManagement();
        Resource resource = null;

        try {
            resource = downloadUtil.getFileAsResourse("photos/candidate/" + userId, fileName);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);

    }
}
