package com.everee.api.controller;


import com.everee.api.exceptions.ResourceNotFoundException;
import com.everee.api.model.Company;
import com.everee.api.repository.CompanyRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyRepository companyRepository;


    @Test
    public void givenCompanies_whenGetCompanies_thenReturnJsonArray()
            throws Exception {

        Company company = createMockCompany();

        List<Company> allCompanies = Arrays.asList(company);

        given(companyRepository.findAll()).willReturn(allCompanies);

        //mock call to the API with the call to the DB mooked out in the preceding given() method
        mvc.perform(get("/companies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(company.getName())));
    }

    @Test
    public void givenCompany_whenGetCompanyyId_thenReturnJson()
            throws Exception {

        Company company = createMockCompany();
        Optional<Company> opt = Optional.of(company);

        given(companyRepository.findById(company.getId())).willReturn(opt);

        //mock call to the API with the call to the DB mooked out in the preceding given() method
        mvc.perform(get("/companies/{id}", company.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(company.getName())));
    }

    @Test
    public void whenGetCompanyyId_NotFound_thenReturnErrorJson()
            throws Exception {

        given(companyRepository.findById(1l)).willThrow(new ResourceNotFoundException(1l,"Not Found"));

        //mock call to the API with the call to the DB mooked out in the preceding given() method
        mvc.perform(get("/companies/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.errorCode", is("404")));
    }


    @Test
    public void addCompany_ReturnSuccess() throws Exception {
        Company company = createMockCompany();
        company.setId(null);

        given(companyRepository.save(company)).willReturn(company);

        mvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(company)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(company.getName())));

    }

    @Test
    public void addCompany_InvalidArguments() throws Exception {
        Company company = createMockCompany();
        company.setId(null);
        company.setPhone(null);
        company.setName(null);

        given(companyRepository.save(company)).willReturn(company);

        mvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(company)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.errorCode", is("400")))
                .andExpect(jsonPath("$.errorMessage",containsString("field 'name'")))
                .andExpect(jsonPath("$.errorMessage",containsString("field 'phone'")));

    }

    @Test
    public void updateCompany_ReturnSuccess() throws Exception {
        Company originalCompany = createMockCompany();
        Company updatedCompany = createMockCompany();
        updatedCompany.setName("Some Changed Name");

        Optional<Company> opt = Optional.of(originalCompany);

        given(companyRepository.findById(originalCompany.getId())).willReturn(opt);
        given(companyRepository.save(updatedCompany)).willReturn(updatedCompany);

        mvc.perform(put("/companies/{id}", originalCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(updatedCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(updatedCompany.getName())));

    }

    @Test
    public void updateCompany_InvalidArguments() throws Exception {
        Company originalCompany = createMockCompany();
        Company updatedCompany = createMockCompany();
        updatedCompany.setName(null);

        Optional<Company> opt = Optional.of(originalCompany);

        given(companyRepository.findById(originalCompany.getId())).willReturn(opt);
        given(companyRepository.save(updatedCompany)).willReturn(updatedCompany);

        mvc.perform(put("/companies/{id}", originalCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(updatedCompany)))
                .andExpect(jsonPath("$.errorCode", is("400")))
                .andExpect(jsonPath("$.errorMessage",containsString("field 'name'")));
    }

    @Test
    public void updateCompany_InvalidId() throws Exception {
        Company company = createMockCompany();
        given(companyRepository.findById(1l)).willThrow(new ResourceNotFoundException(1l,"Not Found"));

        mvc.perform(put("/companies/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(company)))
                .andExpect(jsonPath("$.errorCode", is("404")));
    }


    // Utility methods for creating and working with Company objects
    private Company createMockCompany(){
        Company testCompany = new Company();
        testCompany.setId(1l);
        testCompany.setName("Test Company Name");
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

    private byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
