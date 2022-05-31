package com.insurecorp.insureCorp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int claimId;
    private String userName;
    private String patient;
    private String hospital;
    private String diseaseCategory;
    private String disease;
    private Long claimAmount;
    private Date createdAt=new Date();
    @ElementCollection
    private List<String> documentUrl = new ArrayList<>();

}
