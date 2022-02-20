package com.ets.filesystem.web.controller;

import com.ets.filesystem.entity.User;
import com.ets.filesystem.request.*;
import com.ets.filesystem.response.*;
import com.ets.filesystem.web.config.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.security.spec.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class AuthenticationController {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        JWTTokenHelper jWTTokenHelper;

        @Autowired
        private UserDetailsService userDetailsService;

        @PostMapping("/auth/login")
        public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException
        {

            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUserName(), authenticationRequest.getPassword()));
            System.out.println("hello");

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user= (User) authentication.getPrincipal();
            String jwtToken=jWTTokenHelper.generateToken(user.getUsername());

            LoginResponse response=new LoginResponse();
            response.setToken(jwtToken);


            return ResponseEntity.ok(response);
        }

        @GetMapping("/auth/userinfo")
        public ResponseEntity<?> getUserInfo(Principal user){
        User userObj= (User) userDetailsService.loadUserByUsername(user.getName());

        UserInfo userInfo=new UserInfo();
        userInfo.setFirstName(userObj.getFirstName());
        userInfo.setLastName(userObj.getLastName());
        userInfo.setRoles(userObj.getAuthorities().toArray());


        return ResponseEntity.ok(userInfo);




    }
}
