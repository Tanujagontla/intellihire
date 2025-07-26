package com.intellihire.jobportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalJobs;
    private Long activeJobs;
    private Long totalApplications;
    private Long totalCandidates;
    private Long totalCompanies;
    private Long pendingApplications;
    private Long acceptedApplications;
    private Long rejectedApplications;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }

    // Constructors
    public Statistics() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTotalJobs() { return totalJobs; }
    public void setTotalJobs(Long totalJobs) { this.totalJobs = totalJobs; }

    public Long getActiveJobs() { return activeJobs; }
    public void setActiveJobs(Long activeJobs) { this.activeJobs = activeJobs; }

    public Long getTotalApplications() { return totalApplications; }
    public void setTotalApplications(Long totalApplications) { this.totalApplications = totalApplications; }

    public Long getTotalCandidates() { return totalCandidates; }
    public void setTotalCandidates(Long totalCandidates) { this.totalCandidates = totalCandidates; }

    public Long getTotalCompanies() { return totalCompanies; }
    public void setTotalCompanies(Long totalCompanies) { this.totalCompanies = totalCompanies; }

    public Long getPendingApplications() { return pendingApplications; }
    public void setPendingApplications(Long pendingApplications) { this.pendingApplications = pendingApplications; }

    public Long getAcceptedApplications() { return acceptedApplications; }
    public void setAcceptedApplications(Long acceptedApplications) { this.acceptedApplications = acceptedApplications; }

    public Long getRejectedApplications() { return rejectedApplications; }
    public void setRejectedApplications(Long rejectedApplications) { this.rejectedApplications = rejectedApplications; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
