package org.example.security;

import org.example.entities.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import lombok.extern.log4j.Log4j;

@Log4j
public class MyAuthentication implements Authentication {
    Account account;
    public MyAuthentication(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.debug("getAuthorities");
        return null != account ? account.getPermissions() : null;
//        return null;
    }

    @Override
    public Object getCredentials() {
        log.debug("getCredentials");
        return null;
    }

    @Override
    public Object getDetails() {
        log.debug("getDetails");
        return null;
    }

    @Override
    public Object getPrincipal() {
        log.debug("getPrincipal()");
        return account;
    }

    @Override
    public boolean isAuthenticated() {
        log.debug(String.format("isAuthenticated = %b", null != account));
        return null != account;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        log.debug(String.format("setAutheticated(%b)", isAuthenticated));
    }

    @Override
    public String getName() {
        return (null != account) ? account.getName() : null;
    }
}
