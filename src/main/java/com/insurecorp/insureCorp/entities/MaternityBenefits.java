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
public class MaternityBenefits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maternityBenefitId;
    private double cSectionDelivery;
    private double normalDelivery;
}
