package com.ets.filesystem.service;

import com.ets.filesystem.entity.User;
import com.ets.filesystem.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDetailsRepository.findByUserName(username);

        if(user==null){
            throw  new UsernameNotFoundException("USER not found in Database");
        }
        return user;
    }
}
