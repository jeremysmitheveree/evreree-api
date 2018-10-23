package com.everee.api.controller;


import com.everee.api.exceptions.ResourceNotFoundException;
import com.everee.api.model.Employee;
import com.everee.api.repository.CompanyRepository;
import com.everee.api.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;


    @GetMapping("/employee/{companyId}")
    public List<Employee> getEmployees(@PathVariable Long companyId) {
        return employeeRepository.findByCompanyIdOrderByLastName(companyId);
    }

    @GetMapping("/employee/{companyId}/{employeeId}")
    public Employee getEmployees(@PathVariable Long companyId, @PathVariable Long employeeId){

        //should be using companyId as well - security reasons - TODO research this
        return employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResourceNotFoundException(employeeId, "Employee not found with id = " + employeeId ));
    }

    @PostMapping("/employee/{companyId}")
    public Employee addEmployee(@PathVariable Long companyId, @Valid @RequestBody Employee employee){
        if (!companyRepository.existsById(companyId)) {
            throw new ResourceNotFoundException(companyId, "Company not found with id " + companyId);
        }

        return employeeRepository.save(employee);
    }


    @PutMapping("/employee/{companyId}/{employeeId}")
    public Employee updateEmployee(@PathVariable Long companyId, @PathVariable Long employeeId,
                                  @Valid @RequestBody Employee employeeRequest){

        if (!companyRepository.existsById(companyId)) {
            throw new ResourceNotFoundException(companyId, "Company not found with id " + companyId);
        }

        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    employee.setFirstName(employeeRequest.getFirstName());
                    employee.setLastName(employeeRequest.getLastName());
                    employee.setTitle(employeeRequest.getTitle());
                    employee.setDepartment(employeeRequest.getDepartment());
                    employee.setI9citizen(employeeRequest.isI9citizen());
                    employee.setResidentState(employeeRequest.getResidentState());
                    employee.setCompanyEmployeeId(employeeRequest.getCompanyEmployeeId());
                    employee.setStartDate(employeeRequest.getStartDate());
                    employee.setEndDate(employeeRequest.getEndDate());
                    employee.setActive(employee.isActive());
                    return employeeRepository.save(employee);
                }).orElseThrow(() -> new ResourceNotFoundException(employeeId, "Company not found with id = " + employeeId));
    }
}
