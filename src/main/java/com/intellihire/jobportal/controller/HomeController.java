package com.intellihire.jobportal.controller;

import com.intellihire.jobportal.model.JobPost;
import com.intellihire.jobportal.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private JobPostService jobPostService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        List<JobPost> recentJobs = jobPostService.findActiveJobs();
        // Limit to 6 recent jobs for home page
        if (recentJobs.size() > 6) {
            recentJobs = recentJobs.subList(0, 6);
        }
        model.addAttribute("recentJobs", recentJobs);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
