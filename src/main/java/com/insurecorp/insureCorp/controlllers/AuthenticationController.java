package com.insurecorp.insureCorp.controlllers;

import com.insurecorp.insureCorp.entities.User;
import com.insurecorp.insureCorp.repositories.UserRepository;
import com.insurecorp.insureCorp.requestModels.LoginRequest;
import com.insurecorp.insureCorp.responseModels.LoginResponse;
import com.insurecorp.insureCorp.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    LoginService loginService;
    @Autowired
    UserRepository userRepository;
@PostMapping("/log-in")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws Exception {
        String jwt =loginService.login(loginRequest);
    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setJwt(jwt);
    loginResponse.setMessage("login successful");
    User user = userRepository.findUserByEmail(loginRequest.getUsername()).get(0);
    loginResponse.setRole(user.getRole().getRole());
    System.out.println("hello");
        return loginResponse;
    }
}
