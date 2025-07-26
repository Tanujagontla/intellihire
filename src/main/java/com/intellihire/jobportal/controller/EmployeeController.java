package com.intellihire.jobportal.controller;

import com.intellihire.jobportal.model.JobPost;
import com.intellihire.jobportal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.intellihire.jobportal.model.Application;

import java.util.List;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeController {

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobCategoryService jobCategoryService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        
        // Get statistics for HR dashboard
        long totalJobs = jobPostService.findAllJobPosts().size();
        long totalApplications = applicationService.findAllApplications().size();
        
        model.addAttribute("totalJobs", totalJobs);
        model.addAttribute("totalApplications", totalApplications);
        model.addAttribute("username", username);
        
        // Get recent jobs posted by this user or all jobs
        model.addAttribute("recentJobs", jobPostService.findAllJobPosts().stream().limit(5).toList());
        model.addAttribute("recentApplications", applicationService.findAllApplications().stream().limit(5).toList());

        return "employee/dashboard";
    }

    @GetMapping("/jobs")
    public String jobs(Model model) {
        model.addAttribute("jobs", jobPostService.findAllJobPosts());
        return "employee/jobs";
    }

    @GetMapping("/jobs/new")
    public String newJob(Model model) {
        model.addAttribute("jobPost", new JobPost());
        model.addAttribute("companies", companyService.findAllCompanies());
        model.addAttribute("categories", jobCategoryService.findAllJobCategories());
        return "employee/job-form";
    }

    @PostMapping("/jobs")
    public String createJob(@ModelAttribute JobPost jobPost, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            // Set the user who posted the job
            var user = userService.findByUsername(authentication.getName());
            if (user.isPresent()) {
                jobPost.setPostedBy(user.get());
            }
            
            // Set default company if not selected
            if (jobPost.getCompany() == null) {
                var companies = companyService.findAllCompanies();
                if (!companies.isEmpty()) {
                    jobPost.setCompany(companies.get(0));
                }
            }
            
            // Set default category if not selected
            if (jobPost.getCategory() == null) {
                var categories = jobCategoryService.findAllJobCategories();
                if (!categories.isEmpty()) {
                    jobPost.setCategory(categories.get(0));
                }
            }
            
            jobPostService.saveJobPost(jobPost);
            redirectAttributes.addFlashAttribute("successMessage", "Job posted successfully!");
            return "redirect:/employee/jobs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error posting job: " + e.getMessage());
            return "redirect:/employee/jobs/new";
        }
    }

    @GetMapping("/applications")
    public String applications(Model model, Authentication authentication) {
        // Get all applications for now - in a real app, you'd filter by company
        List<Application> applications = applicationService.findAllApplications();
        model.addAttribute("applications", applications);
        return "employee/applications";
    }
}
