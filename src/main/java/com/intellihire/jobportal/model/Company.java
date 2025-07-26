package com.intellihire.jobportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name must not exceed 100 characters")
    @Column(unique = true)
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private String website;
    private String address;
    private String phone;
    private String logo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Admin> admins;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JobPost> jobPosts;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Company() {}

    public Company(String name, String description, String website, String address, String phone) {
        this.name = name;
        this.description = description;
        this.website = website;
        this.address = address;
        this.phone = phone;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<Admin> getAdmins() { return admins; }
    public void setAdmins(List<Admin> admins) { this.admins = admins; }

    public List<JobPost> getJobPosts() { return jobPosts; }
    public void setJobPosts(List<JobPost> jobPosts) { this.jobPosts = jobPosts; }
}
