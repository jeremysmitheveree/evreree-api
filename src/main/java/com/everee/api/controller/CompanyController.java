package com.everee.api.controller;


import com.everee.api.exceptions.ResourceNotFoundException;
import com.everee.api.model.Company;
import com.everee.api.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *  Controller for the "company" related resources.  Main items of note:
 *      CompanyRepository - JPA repository for company database representation, currently using the extended methods
 *      available on the repository
 *
 *      Mappings - {baseURL = /api/v1 }/companies/XXX
 */

@RestController
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/companies")
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @GetMapping("/companies/{companyId}")
    public Company getCompany(@PathVariable Long companyId){
        return companyRepository.findById(companyId).orElseThrow(() ->
                new ResourceNotFoundException(companyId, "Company not found with id = " + companyId ));
    }

    @PostMapping("/companies")
    public Company addCompany(@Valid @RequestBody Company company){
        return companyRepository.save(company);
    }

    @PutMapping("/companies/{companyId}")
    public Company updateCompany(@PathVariable Long companyId, @Valid @RequestBody Company companyRequest){
        return companyRepository.findById(companyId)
                .map(company -> {
                    company.setName(companyRequest.getName());
                    company.setDescription(companyRequest.getDescription());
                    company.setCreatedDate(companyRequest.getCreatedDate());
                    company.setActive(companyRequest.isActive());
                    company.setFirstName(companyRequest.getFirstName());
                    company.setLastName(companyRequest.getLastName());
                    company.setEmail(companyRequest.getEmail());
                    company.setPhone(companyRequest.getPhone());
                    company.setCountry(companyRequest.getCountry());
                    company.setState(companyRequest.getState());
                    company.setZip(companyRequest.getZip());
                    return companyRepository.save(company);
                }).orElseThrow(() -> new ResourceNotFoundException(companyId, "Company not found with id = " + companyId));
    }

}
