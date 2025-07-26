package com.intellihire.jobportal.controller;

import com.intellihire.jobportal.model.Candidate;
import com.intellihire.jobportal.model.JobType;
import com.intellihire.jobportal.model.User;
import com.intellihire.jobportal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/candidate")
@PreAuthorize("hasRole('CANDIDATE')")
public class CandidateController {

  @Autowired
  private JobPostService jobPostService;

  @Autowired
  private ApplicationService applicationService;

  @Autowired
  private CandidateService candidateService;

  @Autowired
  private UserService userService;

  @Autowired
  private JobCategoryService jobCategoryService; // Added this line

  @GetMapping("/dashboard")
  public String dashboard(Model model, Authentication authentication) {
      String username = authentication.getName();
      
      // Get available jobs
      long totalJobs = jobPostService.findActiveJobs().size(); // Changed to findActiveJobs
      
      model.addAttribute("totalJobs", totalJobs);
      model.addAttribute("username", username);
      
      // Get recent jobs
      model.addAttribute("recentJobs", jobPostService.findActiveJobs().stream().limit(6).toList());

      return "candidate/dashboard";
  }

  @GetMapping("/jobs")
  public String jobs(Model model) {
      model.addAttribute("jobs", jobPostService.findActiveJobs());
      model.addAttribute("categories", jobCategoryService.findAllJobCategories()); // Added for search filters
      model.addAttribute("jobTypes", JobType.values()); // Added for search filters
      return "candidate/jobs";
  }

  @GetMapping("/applications")
  public String applications(Model model, Authentication authentication) {
      String username = authentication.getName();
      Optional<User> userOptional = userService.findByUsername(username);
      if (userOptional.isPresent() && userOptional.get() instanceof Candidate) {
          Candidate candidate = (Candidate) userOptional.get();
          model.addAttribute("applications", applicationService.findByCandidate(candidate));
      } else {
          model.addAttribute("applications", List.of()); // No applications if not a candidate
      }
      return "candidate/applications";
  }

  @GetMapping("/profile")
  public String profile(Model model, Authentication authentication) {
      String username = authentication.getName();
      Optional<User> userOptional = userService.findByUsername(username);
      if (userOptional.isPresent() && userOptional.get() instanceof Candidate) {
          Candidate candidate = (Candidate) userOptional.get();
          model.addAttribute("candidate", candidate);
      } else {
          // Handle case where user is not a candidate or not found
          model.addAttribute("error", "Candidate profile not found.");
      }
      return "candidate/profile";
  }
}
