package com.insurecorp.insureCorp.services;

import com.insurecorp.insureCorp.constants.SecurityConstants;
import com.insurecorp.insureCorp.entities.User;
import com.insurecorp.insureCorp.repositories.UserRepository;
import com.insurecorp.insureCorp.requestModels.LoginRequest;
import com.insurecorp.insureCorp.userManagement.JwtUtils;
import com.insurecorp.insureCorp.userManagement.MyUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private UserRepository userRepository;

    public String login(LoginRequest loginRequest) throws Exception {
        System.out.println(1);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        System.out.println(myUserService.loadUserByUsername(loginRequest.getUsername()).getAuthorities());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwt = "";
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        jwt = Jwts.builder().setIssuer("My Project").setSubject("JWT Token")
                .claim("username", loginRequest.getUsername())
                .claim("authorities", populateAuthorities(myUserService.loadUserByUsername(loginRequest.getUsername()).getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 300000000))
                .signWith(key).compact();
        return jwt;
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

    public User getUser (String jwt)
    {
        JwtUtils userInfo = new JwtUtils();
        userInfo.processJWT(jwt);
        System.out.println(userInfo.getUsername());
             User user = userRepository.findUserByEmail(userInfo.getUsername()).get(0);

        System.out.println("here");
        return user;
    }
}
