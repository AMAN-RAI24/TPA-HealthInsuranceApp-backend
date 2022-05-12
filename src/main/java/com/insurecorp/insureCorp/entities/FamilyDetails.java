package com.insurecorp.insureCorp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class FamilyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int familyDetailsId;
    private int adults;
    private int children;
}
