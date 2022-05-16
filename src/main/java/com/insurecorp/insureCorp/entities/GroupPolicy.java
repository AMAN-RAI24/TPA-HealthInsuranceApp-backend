package com.insurecorp.insureCorp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class GroupPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupPolicyId;
    private String policyName;

    @OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private User Manager;
    @ManyToOne(cascade= CascadeType.MERGE,fetch = FetchType.EAGER)
    private Company company;
    private String type;
    private Date creationDate = new Date();
    private double coverage;
    private int hospitalTier;
    private int roomRentLimit;
    private boolean diagnosticTest;


    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private MaximumClaim maximumClaim;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private EmployeeDistribution employeeDistribution;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private MaternityBenefits maternityBenefits;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private FamilyDetails familyDetails;
}
