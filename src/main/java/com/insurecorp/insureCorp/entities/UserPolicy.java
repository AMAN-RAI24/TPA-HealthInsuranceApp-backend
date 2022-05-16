package com.insurecorp.insureCorp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class UserPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userPolicyId;
    @OneToOne
    private User user;
    @ManyToOne
    private GroupPolicy groupPolicy;
    private double coverage;
    @OneToMany
    private List<UserFamilyDetails> userFamilyDetails;

//    @OneToMany
//    private UserFamilyDetails userFamilyDetails;
//

//    @OneToOne
//    private List<UserFamilyDetails> userFamilyDetails;



}
