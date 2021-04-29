package org.example.sweater.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Ivan Kurilov on 22.04.2021
 */
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
