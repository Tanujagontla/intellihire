package com.intellihire.jobportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String department;
    private String position;

    // Constructors
    public Admin() {
        super();
    }

    public Admin(String username, String email, String password, String firstName, String lastName, Company company, String department, String position) {
        super(username, email, password, firstName, lastName, Role.ADMIN);
        this.company = company;
        this.department = department;
        this.position = position;
    }

    // Getters and Setters
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}
