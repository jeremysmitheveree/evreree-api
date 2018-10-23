package com.everee.api.repository;

import com.everee.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long> {

    // Enabling static ORDER BY for a query
    List<Employee> findByCompanyIdOrderByLastName(Long companyId);

}
