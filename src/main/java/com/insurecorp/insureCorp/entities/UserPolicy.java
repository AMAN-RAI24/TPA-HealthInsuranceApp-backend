package com.insurecorp.insureCorp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class UserPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userPolicyId;
    @OneToOne
    private User user;
    @OneToOne
    private GroupPolicy groupPolicy;
    private double coverage;
    @OneToOne
    private FamilyDetails familyDetails;

}
