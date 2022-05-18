package com.insurecorp.insureCorp.responseModels;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PolicyAddedResponse {
        private int policyId;
        private String policyName;
}
