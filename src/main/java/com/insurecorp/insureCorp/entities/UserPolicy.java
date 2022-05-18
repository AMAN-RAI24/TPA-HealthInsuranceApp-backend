package com.insurecorp.insureCorp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class UserPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userPolicyId;
    @OneToOne
    private User user;
    @ManyToOne
    private GroupPolicy groupPolicy;
    private double coverage;
    @OneToMany(cascade = CascadeType.ALL)
    private List<UserFamilyDetails> userFamilyDetails;



//    @OneToMany
//    private UserFamilyDetails userFamilyDetails;
//

//    @OneToOne
//    private List<UserFamilyDetails> userFamilyDetails;



}
