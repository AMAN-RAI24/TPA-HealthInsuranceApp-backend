package com.insurecorp.insureCorp.userManagement;

import com.insurecorp.insureCorp.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Data
@NoArgsConstructor
public class JwtUtils {
    private String username;
    private String authorities;

    public void processJWT(String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(
                SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        this.username = String.valueOf(claims.get("username"));
        this.authorities = (String) claims.get("authorities");
        System.out.println("1" + username + "2" + authorities);
    }
}
