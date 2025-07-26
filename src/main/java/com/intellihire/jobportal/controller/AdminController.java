package com.intellihire.jobportal.controller;

import com.intellihire.jobportal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Get statistics for dashboard
        long totalUsers = userService.findAllUsers().size();
        long totalJobs = jobPostService.findAllJobPosts().size();
        long totalCompanies = companyService.findAllCompanies().size();
        long totalApplications = applicationService.findAllApplications().size();

        // Get recent data
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalJobs", totalJobs);
        model.addAttribute("totalCompanies", totalCompanies);
        model.addAttribute("totalApplications", totalApplications);
        
        model.addAttribute("recentJobs", jobPostService.findAllJobPosts().stream().limit(5).toList());
        model.addAttribute("recentUsers", userService.findAllUsers().stream().limit(5).toList());

        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/users";
    }

    @GetMapping("/jobs")
    public String jobs(Model model) {
        model.addAttribute("jobs", jobPostService.findAllJobPosts());
        return "admin/jobs";
    }

    @GetMapping("/companies")
    public String companies(Model model) {
        model.addAttribute("companies", companyService.findAllCompanies());
        return "admin/companies";
    }

    @GetMapping("/applications")
    public String applications(Model model) {
        model.addAttribute("applications", applicationService.findAllApplications());
        return "admin/applications";
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportData() {
        StringBuilder csvData = new StringBuilder();
        
        // CSV Header
        csvData.append("Data Export - IntelliHire Job Portal\n");
        csvData.append("Generated on: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
        
        // Users Data
        csvData.append("USERS\n");
        csvData.append("ID,Username,Email,First Name,Last Name,Role,Created At\n");
        userService.findAllUsers().forEach(user -> {
            csvData.append(user.getId()).append(",")
                   .append(user.getUsername()).append(",")
                   .append(user.getEmail()).append(",")
                   .append(user.getFirstName()).append(",")
                   .append(user.getLastName()).append(",")
                   .append(user.getRole()).append(",")
                   .append(user.getCreatedAt() != null ? user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A")
                   .append("\n");
        });
        
        csvData.append("\nJOBS\n");
        csvData.append("ID,Title,Company,Location,Job Type,Experience Level,Min Salary,Max Salary,Posted Date\n");
        jobPostService.findAllJobPosts().forEach(job -> {
            csvData.append(job.getId()).append(",")
                   .append("\"").append(job.getTitle()).append("\"").append(",")
                   .append("\"").append(job.getCompany().getName()).append("\"").append(",")
                   .append("\"").append(job.getLocation()).append("\"").append(",")
                   .append(job.getJobType()).append(",")
                   .append(job.getExperienceLevel()).append(",")
                   .append(job.getMinSalary()).append(",")
                   .append(job.getMaxSalary()).append(",")
                   .append(job.getCreatedAt() != null ? job.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A")
                   .append("\n");
        });
        
        csvData.append("\nCOMPANIES\n");
        csvData.append("ID,Name,Description,Website,Address,Phone\n");
        companyService.findAllCompanies().forEach(company -> {
            csvData.append(company.getId()).append(",")
                   .append("\"").append(company.getName()).append("\"").append(",")
                   .append("\"").append(company.getDescription() != null ? company.getDescription().replace("\"", "\"\"") : "").append("\"").append(",")
                   .append(company.getWebsite() != null ? company.getWebsite() : "").append(",")
                   .append("\"").append(company.getAddress() != null ? company.getAddress() : "").append("\"").append(",")
                   .append(company.getPhone() != null ? company.getPhone() : "")
                   .append("\n");
        });

        String filename = "intellihire_export_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvData.toString());
    }
}
