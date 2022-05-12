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
public class EmployeeDistribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeDistributionId;
    private int ageGroup1;
    private int ageGroup2;
    private int ageGroup3;
    private int ageGroup4;
    private int males;
    private int females;
    private int total;
}
