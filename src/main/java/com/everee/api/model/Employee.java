package com.everee.api.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="companyid")
    private Long companyId;

    @Column(name = "firstname")
    @NotNull
    private String firstName;

    @Column(name = "lastname")
    @NotNull
    private String lastName;

    private String title;

    private String department;

    private boolean i9citizen;

    @Column(name="residentstate")
    private String residentState;

    @Column(name="companyemployeeid")
    private String companyEmployeeId;

    @Column(name="startdate")
    private Date startDate;

    @Column(name="enddate")
    private Date endDate;

    @NotNull
    private boolean active;

}
