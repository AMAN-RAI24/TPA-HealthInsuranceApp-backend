package com.insurecorp.insureCorp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class ClaimDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int claimId;
    private String patient;
    private String hospital;
    private String diseaseCategory;
    private String disease;
    private Long claimAmount;

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDiseaseCategory() {
        return diseaseCategory;
    }

    public void setDiseaseCategory(String diseaseCategory) {
        this.diseaseCategory = diseaseCategory;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Long getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Long claimAmount) {
        this.claimAmount = claimAmount;
    }
}
