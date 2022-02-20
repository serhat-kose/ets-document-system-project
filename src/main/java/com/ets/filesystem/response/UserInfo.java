package com.ets.filesystem.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String firstName;
    private String lastName;
    private String userName;

    private Object roles;
}
