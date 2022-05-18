package com.insurecorp.insureCorp.responseModels;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Policies {
    private int policyId;
    private String policyName;
    private int benefits ;
    private double coverage;
}
