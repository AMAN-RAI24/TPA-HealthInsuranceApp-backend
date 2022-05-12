package com.insurecorp.insureCorp.userManagement;
import com.insurecorp.insureCorp.entities.User;
import com.insurecorp.insureCorp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findUserByEmail(username);
        if (users.size() == 0) {
            throw new UsernameNotFoundException(" Not found ");
        }
        return new MyUserDetails(users.get(0));
    }
}