package com.intellihire.jobportal.service.impl;

import com.intellihire.jobportal.model.Company;
import com.intellihire.jobportal.repository.CompanyRepository;
import com.intellihire.jobportal.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Optional<Company> findByName(String name) {
        return companyRepository.findByName(name);
    }

    @Override
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public boolean existsByName(String name) {
        return companyRepository.existsByName(name);
    }
}
