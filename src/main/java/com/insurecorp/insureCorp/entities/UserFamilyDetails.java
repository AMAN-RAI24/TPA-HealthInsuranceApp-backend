package com.insurecorp.insureCorp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class UserFamilyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userFamilyDetailsId;
    private String name;
    private String relation;
    private Integer age;
    private String phoneNumber;
    private String imageUrl;
//    @ManyToOne
//    @JoinColumn(name = "user_policy_id")
//    private UserPolicy userPolicy;
}
