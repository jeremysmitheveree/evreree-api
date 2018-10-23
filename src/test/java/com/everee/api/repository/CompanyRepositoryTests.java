package com.everee.api.repository;


import com.everee.api.model.Company;
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
public class CompanyRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyRepository companyRepository;

    // Integration test using a temporary in memory DB (H2)
    @Test
    public void whenFindById_thenReturnCompany() {
        // given
        Company testCompany = createDummyCompany(1l);
        Company mergedCompany = entityManager.merge(testCompany);
        entityManager.flush();

        List<Company> x = companyRepository.findAll();

        // when
        Company found = companyRepository.findById(mergedCompany.getId()).orElse(null);

        // then
        if(found != null) {
            assertThat(found.getName())
                    .isEqualTo(testCompany.getName());
        }else{
            fail("Test Company not found");

        }
    }

    @Test
    public void whenFindAll_thenReturnCompanies() {
        // given
        Company testCompany1 = createDummyCompany(2l);
        Company testCompany2 = createDummyCompany(3l);
        entityManager.merge(testCompany1);
        entityManager.merge(testCompany2);
        entityManager.flush();

        // when
        List<Company> found = companyRepository.findAll();

        // then
        if(found != null) {
            assertThat(found.size() == 2);
            assertThat(found.get(0).getName())
                    .isEqualToIgnoringCase(testCompany1.getName());

        }else{
            fail("Test Companies are not found");

        }
    }


    private Company createDummyCompany(Long id){

        Company testCompany = new Company();
        testCompany.setId(id);
        testCompany.setName("Test Company Name " + id);
        testCompany.setDescription("Some Description");
        testCompany.setActive(true);
        testCompany.setCreatedDate(new Date());
        testCompany.setFirstName("Test_first_name");
        testCompany.setLastName("Test_last_name");
        testCompany.setEmail("somedumb@email.email");
        testCompany.setPhone("123.123.1234");
        testCompany.setCountry("USA");
        testCompany.setState("UT");
        testCompany.setZip("12345");

        return testCompany;
    }

}


