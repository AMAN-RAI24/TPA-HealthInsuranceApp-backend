package com.insurecorp.insureCorp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(unique = true)
    private String employeeId;
    private String email;
    private String name;
    private String mobileNumber;
    private Date date;
    private String password;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Company company;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Role role;
    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
