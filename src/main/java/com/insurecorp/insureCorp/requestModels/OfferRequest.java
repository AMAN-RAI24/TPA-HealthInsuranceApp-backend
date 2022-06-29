package com.insurecorp.insureCorp.requestModels;

import com.insurecorp.insureCorp.entities.GroupPolicy;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfferRequest {
private double amount;
private String message;
private int groupPolicyId;
}
