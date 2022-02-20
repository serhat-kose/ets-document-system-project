package com.ets.filesystem.entity;

import lombok.*;
import org.springframework.security.core.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AUTH_AUTHORITY")
@Entity
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name = "ROLE_CODE")
    private String roleCode;

    @Column(name = "ROLE_DESC")
    private String roleDesc;

    @Override
    public String getAuthority() {
        return roleCode;
    }
}
