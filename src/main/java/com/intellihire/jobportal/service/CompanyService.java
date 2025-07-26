package com.intellihire.jobportal.service;

import com.intellihire.jobportal.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Company saveCompany(Company company);
    Optional<Company> findById(Long id);
    Optional<Company> findByName(String name);
    List<Company> findAllCompanies();
    void deleteCompany(Long id);
    Company updateCompany(Company company);
    boolean existsByName(String name);
}
