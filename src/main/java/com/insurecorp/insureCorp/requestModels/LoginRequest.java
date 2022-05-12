package com.insurecorp.insureCorp.requestModels;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
private String username;
private String password;
}
