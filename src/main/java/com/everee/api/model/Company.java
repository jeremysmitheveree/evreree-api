package com.everee.api.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 *  Primary company resource model (POJO).  Extensive leveraging of JPA and Spring Data annotations to keep the boiler
 *  plate code to a minimum
 *
 *  Data - injects getter/setter/toString/hashcode methods with standard implementations at compile time
 *  Entity - is the designates this as the DTO for the company resource and scopes the Id/GeneratedValue/Column
 *  annotations that define the mapping to the evereeDB.company table
 *  Validation - annotations NotNull/Email etc... provide basic validation methods for the properties in the POJO
 */

@Data
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    @Column(name = "createddate")
    @NotNull
    private Date createdDate;

    @NotNull
    private boolean active;

    @Column(name = "firstname")
    @NotNull
    private String firstName;

    @Column(name = "lastname")
    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String country;

    @NotNull
    private String state;

    @NotNull
    private String zip;

}
