package org.example.rest;

import lombok.extern.log4j.Log4j;
import org.example.App;
import org.example.entities.Account;
import org.example.repositories.AccountRepository;
import org.example.repositories.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Log4j
@RestController
public class GreetingCotroller {

//    @Autowired
    MyRepository repo = App.repo;// = new AccountRepository();
    AtomicLong counter = new AtomicLong();

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/greeting")
    public  Greeting greeting(@RequestParam(defaultValue = "World") String name, Authentication auth) {
        var account = (Account)auth.getPrincipal();
        return new Greeting(counter.incrementAndGet(), String.format("Hello, %s", account.getName()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/accounts")
    public List<Account> getAll() {
        try {
            return repo.getAllUsers();
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;

    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/account")
    public ResponseEntity<Account> updateAccount(
            String login,
            String name,
            Authentication auth
    ) {
        try {
            var account = (Account)auth.getPrincipal();
            repo.updateUser(account.getId(), login, name);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/account")
    public ResponseEntity deleteAccount(Authentication auth) {
        try{
            if (auth.getPrincipal() instanceof Account) {
                repo.delete(((Account)auth.getPrincipal()).getId());
            }
            else
                throw new IllegalArgumentException("Authorization error.");
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sign-in")
    public String token(
            @RequestParam String login
    ) {
        try {
            return repo.addUser(login);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
