package com.insurecorp.insureCorp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class GroupPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupPolicyId;
    private String policyName;
    @OneToOne
    private User Manager;
    private String type;
    private Date creationDate;
    private double coverage;
    private int hospitalTier;
    private int roomRentLimit;
    private boolean diagnosticTest;
    @OneToOne
    private MaximumClaim maximumClaim;
    @OneToOne
    private EmployeeDistribution employeeDistribution;
    @OneToOne
    private MaternityBenefits maternityBenefits;
    @OneToOne
    private FamilyDetails familyDetails;
}
