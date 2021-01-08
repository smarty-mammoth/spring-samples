package org.example.config;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.security.MyAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.example.App.repo;

@Log4j
public class AuthProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public AuthProcessingFilter() {
        super("/");
//        super(new AntPathRequestMatcher("/login", "POST"));

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
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
            }
        }
        else if (null == repo) {
            log.error("Repo is null.");
        }

//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
