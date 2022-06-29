package com.insurecorp.insureCorp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double amount;
    private String message;
    @ManyToOne
    GroupPolicy groupPolicy;
    @ManyToOne
    InsuranceCompany insuranceCompany;
    String status;

}
