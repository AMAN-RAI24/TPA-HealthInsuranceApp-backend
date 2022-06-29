package com.insurecorp.insureCorp.responseModels;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class InsuranceAcceptedPlans {
    String policyName;
    String status;
    double coverage;
    Date creationDate;
    int id;
}
