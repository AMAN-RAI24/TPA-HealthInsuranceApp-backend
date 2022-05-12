package com.insurecorp.insureCorp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class MaximumClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maximumClaimId;
    private double angiography;
    private double bypassSurgery;
    private double cataractSurgery;
    private double covidCoverage;
    private double hospitalization;
}
