package org.example.security;

import lombok.extern.log4j.Log4j;
import org.example.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class MyUserDetailsService implements UserDetailsService {
//    @Autowired
    AccountRepository repo = new AccountRepository();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (null == repo) {
            log.error("Account repository is null.");
            return null;
        }

        var foundUser = repo.findByLogin(username);
        if (!foundUser.isPresent()) {
            log.error(String.format("Login %s not found.", username));
            return  null;
        }

        return null;
//        return foundUser.get();
    }
}
