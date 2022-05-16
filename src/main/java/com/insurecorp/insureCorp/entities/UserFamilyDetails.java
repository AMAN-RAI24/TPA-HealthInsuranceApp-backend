package com.insurecorp.insureCorp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class UserFamilyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userFamilyDetailsId;
    private String name;
    private String relation;
    private Integer age;
    private String number;
    private String url;
}
