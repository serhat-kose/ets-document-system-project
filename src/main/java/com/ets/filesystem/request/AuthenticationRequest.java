package com.ets.filesystem.request;

import lombok.*;

@Data
public class AuthenticationRequest {
    private String userName;
    private String password;
}
