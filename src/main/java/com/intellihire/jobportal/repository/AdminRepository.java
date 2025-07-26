package com.intellihire.jobportal.repository;

import com.intellihire.jobportal.model.Admin;
import com.intellihire.jobportal.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findByCompany(Company company);
}
