package org.example.config;

import lombok.extern.log4j.Log4j;
import org.example.App;
import org.example.entities.Account;
import org.example.repositories.AccountRepository;
import org.example.repositories.MyRepository;
import org.example.security.MyAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

//@Component
@Log4j
public class AuthTokenFilter extends OncePerRequestFilter {
//    @Autowired(required = true)
//    @Qualifier("myrepo")
    private MyRepository repo = App.repo ;// = new AccountRepository();

//    Authentication authentication;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader("AUTH-TOKEN");
        Authentication authentication = null;
        if (null != token && null != repo) {
            var foundAccount = repo.findByToken(token);
            if (foundAccount.isPresent()) {
                var account = foundAccount.get();
                log.debug(String.format("Found user %s.", account.getName()));
                authentication = new MyAuthentication(account);
            }
            else {
                log.debug(String.format("User with token='%s' not found ", token));
//                authentication = new AnonymousAuthenticationToken(UUID.randomUUID().toString(), null, null);
            }
        }
        else if (null == repo) {
            log.error("Repo is null.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
