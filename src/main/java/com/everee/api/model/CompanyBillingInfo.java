package com.everee.api.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Entity
@Table(name="companybillinginfo")
public class CompanyBillingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long companyId;

/*
    @NotNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String name;

    @NotNull
    @Column(name="cardnumber")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String cardNumber;

    @NotNull
    @Column(name="securitycode")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String securityCode;

    @NotNull
    @Column(name="expmonth")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String expMonth;

    @NotNull
    @Column(name="expyear")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String expYear;

    @NotNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String zip;
*/

}
