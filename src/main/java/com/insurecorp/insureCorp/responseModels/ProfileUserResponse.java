package com.insurecorp.insureCorp.responseModels;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileUserResponse {
    String dob;
    String name;
    String company;
    String phoneNumber;
}
