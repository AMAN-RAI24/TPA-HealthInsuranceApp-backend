package com.insurecorp.insureCorp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyId;
    private String companyName;
    private String address;
    private String contactInformation;
    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<User> users;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<GroupPolicy> groupPolicies;
}
