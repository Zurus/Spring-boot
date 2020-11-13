package com.boblob.blog.models;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by ADivaev on 11.11.2020.
 */
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
