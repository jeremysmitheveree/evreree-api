package com.everee.api.controller;


import com.everee.api.model.Company;
import com.everee.api.model.Employee;
import com.everee.api.repository.CompanyRepository;
import com.everee.api.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private CompanyRepository companyRepository;


    @Test
    public void givenCompanyAndEmployees_whenFindAll_thenReturnJsonArray()
            throws Exception {

        // Data setup
        Employee employee1 = createMockEmployee(1l);
        Employee employee2 = createMockEmployee(1l);
        employee2.setId(2l);

        List<Employee> allEmployees = Arrays.asList(employee1, employee2);

        given(employeeRepository.findByCompanyIdOrderByLastName(1l)).willReturn(allEmployees);

        //mock call to the API with the call to the DB mooked out in the preceding given() method
        mvc.perform(get("/employees/{companyId}", 1l)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(employee1.getFirstName())));
    }





    // Utility methods
    private Employee createMockEmployee(Long companyId){

        Employee employee = new Employee();

        employee.setCompanyId(companyId);
        employee.setId(1l);
        employee.setFirstName("SomeFirstName");
        employee.setLastName("ALastName");
        employee.setTitle("Senior Title");
        employee.setDepartment("G&A");
        employee.setI9citizen(true);
        employee.setResidentState("UT");
        employee.setCompanyEmployeeId("AB123");
        employee.setStartDate(new Date());
        employee.setEndDate(new Date());
        employee.setActive(true);

        return employee;

    }



}
