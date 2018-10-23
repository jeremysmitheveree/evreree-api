package com.everee.api.repository;


import com.everee.api.model.Company;
import com.everee.api.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Integration test using a temporary in memory DB (H2)
    @Test
    public void whenFindByCompanyIdOrderByLastName_thenReturnEmployees() {
        // given
        Employee testEmployee1 = createMockEmployee(1l);
        Employee testEmployee2 = createMockEmployee(1l);
        testEmployee1.setLastName("ZNameToEndOfLine");

        entityManager.merge(testEmployee1);
        entityManager.merge(testEmployee2);
        entityManager.flush();

        // when
        List<Employee> found = employeeRepository.findByCompanyIdOrderByLastName(1L);

        // then
        if(found != null) {
            assertThat(found.size() == 2);
            assertThat(found.get(0).getLastName())
                    .isEqualToIgnoringCase(testEmployee2.getLastName());

        }else{
            fail("Test Companies are not found");

        }
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
