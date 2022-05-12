package com.insurecorp.insureCorp.responseModels;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    String jwt;
    String message;
    String role;
}

