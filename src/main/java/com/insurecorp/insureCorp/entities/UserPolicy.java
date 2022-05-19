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

    @OneToMany(
//            mappedBy = "userPolicy",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
//    @Column(unique = true)
    private List<UserFamilyDetails> userFamilyDetails = new ArrayList<>();


//    @OneToMany
//    private UserFamilyDetails userFamilyDetails;
//

//    @OneToOne
//    private List<UserFamilyDetails> userFamilyDetails;



}
