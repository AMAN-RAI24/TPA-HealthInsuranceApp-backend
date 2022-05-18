package com.insurecorp.insureCorp.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserPolicyDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int policyId;
    double coverage ;
    ArrayList<UserFamilyDetails> familyDetails;

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public double getCoverage() {
        return coverage;
    }

    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    public ArrayList<UserFamilyDetails> getFamilyDetails() {
        return familyDetails;
    }

    public void setFamilyDetails(ArrayList<UserFamilyDetails> familyDetails) {
        this.familyDetails = familyDetails;
    }

}
