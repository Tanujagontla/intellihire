package com.intellihire.jobportal.controller;

import com.intellihire.jobportal.model.JobCategory;
import com.intellihire.jobportal.model.JobPost;
import com.intellihire.jobportal.model.JobType;
import com.intellihire.jobportal.service.JobCategoryService;
import com.intellihire.jobportal.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class JobController {

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobCategoryService jobCategoryService;

    @GetMapping("/jobs")
    public String showJobs(Model model) {
        List<JobPost> jobs = jobPostService.findActiveJobs();
        List<JobCategory> categories = jobCategoryService.findAllJobCategories();
        
        model.addAttribute("jobs", jobs);
        model.addAttribute("categories", categories);
        model.addAttribute("jobTypes", JobType.values());
        
        return "jobs";
    }

    @GetMapping("/jobs/{id}")
    public String showJobDetails(@PathVariable Long id, Model model) {
        Optional<JobPost> job = jobPostService.findById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get());
            return "job-details";
        }
        return "redirect:/jobs";
    }

    @GetMapping("/jobs/search")
    public String searchJobs(@RequestParam(required = false) String title,
                            @RequestParam(required = false) String location,
                            @RequestParam(required = false) Long categoryId,
                            @RequestParam(required = false) JobType jobType,
                            Model model) {
        
        JobCategory category = null;
        if (categoryId != null) {
            category = jobCategoryService.findById(categoryId).orElse(null);
        }

        List<JobPost> jobs = jobPostService.searchJobs(title, location, category, jobType);
        List<JobCategory> categories = jobCategoryService.findAllJobCategories();
        
        model.addAttribute("jobs", jobs);
        model.addAttribute("categories", categories);
        model.addAttribute("jobTypes", JobType.values());
        model.addAttribute("searchTitle", title);
        model.addAttribute("searchLocation", location);
        model.addAttribute("searchCategoryId", categoryId);
        model.addAttribute("searchJobType", jobType);
        
        return "jobs";
    }
}
