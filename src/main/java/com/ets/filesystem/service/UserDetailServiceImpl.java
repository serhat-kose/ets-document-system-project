package com.ets.filesystem.service;

import com.ets.filesystem.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException
    {
        com.ets.filesystem.entity.User currentUser = userRepository.findByUsername(username);
        UserDetails user = new org.springframework.security.core
                .userdetails.User(username, currentUser.getPassword()
                , true, true, true, true,
                AuthorityUtils.createAuthorityList(currentUser.getRole()));
        return user;
    }
}
