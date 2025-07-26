package com.intellihire.jobportal.controller.api;

import com.intellihire.jobportal.model.*;
import com.intellihire.jobportal.service.ApplicationService;
import com.intellihire.jobportal.service.CandidateService;
import com.intellihire.jobportal.service.JobPostService;
import com.intellihire.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api") // Changed base path to /api
@CrossOrigin(origins = "*")
public class ApplicationApiController {

  @Autowired
  private JobPostService jobPostService;

  @Autowired
  private ApplicationService applicationService;

  @Autowired
  private UserService userService;

  @Autowired
  private CandidateService candidateService;

  @GetMapping("/jobs")
  public ResponseEntity<List<JobPost>> getAllJobs() {
      List<JobPost> jobs = jobPostService.findActiveJobs();
      return ResponseEntity.ok(jobs);
  }

  @GetMapping("/jobs/{id}")
  public ResponseEntity<JobPost> getJobById(@PathVariable Long id) {
      Optional<JobPost> job = jobPostService.findById(id);
      return job.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/jobs")
  @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
  public ResponseEntity<JobPost> createJob(@Valid @RequestBody JobPost jobPost, Authentication authentication) {
      try {
          User currentUser = userService.findByUsername(authentication.getName()).orElse(null);
          if (currentUser == null) {
              return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
          }

          jobPost.setPostedBy(currentUser);
          JobPost savedJob = jobPostService.saveJobPost(jobPost);
          return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }
  }

  @PutMapping("/jobs/{id}")
  @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
  public ResponseEntity<JobPost> updateJob(@PathVariable Long id, @Valid @RequestBody JobPost jobPost, Authentication authentication) {
      Optional<JobPost> existingJob = jobPostService.findById(id);
      if (existingJob.isEmpty()) {
          return ResponseEntity.notFound().build();
      }

      User currentUser = userService.findByUsername(authentication.getName()).orElse(null);
      if (currentUser == null) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      jobPost.setId(id);
      jobPost.setPostedBy(currentUser);
      JobPost updatedJob = jobPostService.updateJobPost(jobPost);
      return ResponseEntity.ok(updatedJob);
  }

  @DeleteMapping("/jobs/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
      Optional<JobPost> job = jobPostService.findById(id);
      if (job.isEmpty()) {
          return ResponseEntity.notFound().build();
      }

      jobPostService.deleteJobPost(id);
      return ResponseEntity.noContent().build();
  }

  @GetMapping("/jobs/search")
  public ResponseEntity<List<JobPost>> searchJobs(
          @RequestParam(required = false) String title,
          @RequestParam(required = false) String location,
          @RequestParam(required = false) Long categoryId,
          @RequestParam(required = false) JobType jobType) {
      
      JobCategory category = null;
      if (categoryId != null) {
          // Assuming JobCategoryService is available or injected
          // For this example, we'll just pass null if not found
          // In a real app, you'd fetch it from JobCategoryService
      }

      List<JobPost> jobs = jobPostService.searchJobs(title, location, category, jobType);
      return ResponseEntity.ok(jobs);
  }

  // New API endpoint for applying to a job
  @PostMapping("/applications/apply")
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<String> applyForJob(@RequestParam("jobPostId") Long jobPostId, Authentication authentication) {
      try {
          User currentUser = userService.findByUsername(authentication.getName()).orElse(null);
          if (!(currentUser instanceof Candidate)) {
              return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only candidates can apply for jobs.");
          }
          Candidate candidate = (Candidate) currentUser;

          Optional<JobPost> jobPostOptional = jobPostService.findById(jobPostId);
          if (jobPostOptional.isEmpty()) {
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job post not found.");
          }
          JobPost jobPost = jobPostOptional.get();

          if (applicationService.existsByCandidateAndJobPost(candidate, jobPost)) {
              String conflictHtml = """
                       <!DOCTYPE html>
                       <html lang=\"en\">
                       <head>
                           <meta charset=\"UTF-8\" />
                           <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />
                           <title>Application Status</title>
                           <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\" />
                       </head>
                       <body class=\"d-flex flex-column justify-content-center align-items-center vh-100 bg-light\">
                           <div class=\"text-center\">
                               <h2 class=\"text-danger mb-3\"><i class=\"fas fa-circle-exclamation me-2\"></i>You have already applied for this job.</h2>
                               <div class=\"mt-4\">
                                   <a href=\"/candidate/dashboard\" class=\"btn btn-primary me-2\">Go to Dashboard</a>
                                   <a href=\"/login\" class=\"btn btn-outline-secondary\">Login Page</a>
                               </div>
                           </div>
                           <script src=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js\"></script>
                           <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>
                       </body>
                       </html>
                       """;
              return ResponseEntity.status(HttpStatus.CONFLICT)
                      .contentType(org.springframework.http.MediaType.TEXT_HTML)
                      .body(conflictHtml);
          }

          Application application = new Application();
          application.setCandidate(candidate);
          application.setJobPost(jobPost);
          application.setCoverLetter("Default cover letter: I am very interested in this position."); // Can be expanded with a form field
          application.setStatus(ApplicationStatus.PENDING);

          applicationService.saveApplication(application);
          String successHtml = """
                       <!DOCTYPE html>
                       <html lang=\"en\">
                       <head>
                           <meta charset=\"UTF-8\" />
                           <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />
                           <title>Application Status</title>
                           <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\" />
                       </head>
                       <body class=\"d-flex flex-column justify-content-center align-items-center vh-100 bg-light\">
                           <div class=\"text-center\">
                               <h2 class=\"text-success mb-3\"><i class=\"fas fa-check-circle me-2\"></i>Application submitted successfully!</h2>
                               <div class=\"mt-4\">
                                   <a href=\"/candidate/dashboard\" class=\"btn btn-success me-2\">Go to Dashboard</a>
                                   <a href=\"/login\" class=\"btn btn-outline-secondary\">Login Page</a>
                               </div>
                           </div>
                           <script src=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js\"></script>
                           <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>
                       </body>
                       </html>
                       """;
          return ResponseEntity.ok()
                  .contentType(org.springframework.http.MediaType.TEXT_HTML)
                  .body(successHtml);
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to submit application: " + e.getMessage());
      }
  }
}
