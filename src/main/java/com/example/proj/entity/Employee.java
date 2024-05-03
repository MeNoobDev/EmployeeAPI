package com.example.proj.entity;


import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String firstName;
    private String lastName;
    private String department;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    private String streetAddress;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String countryCode;
    private String email;
    private String password;

}
