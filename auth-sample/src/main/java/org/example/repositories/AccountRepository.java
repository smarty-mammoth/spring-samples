package org.example.repositories;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.entities.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j
@NoArgsConstructor
//@Component
@Service
public class AccountRepository implements MyRepository {
    private Set<Account> accounts = new HashSet<>(Arrays.asList(
            new Account(1L, "admin@example.org", "Petr", "admin",
                    new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER")))),
            new Account(2L, "user1@example.org", "Ivan", "user1",
                    new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority("USER")))),
            new Account(3L, "user2@example.org", "Serge", "user2",
                    new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority("USER"))))
    ));
    public Optional<Account> findByLogin(String login) {
        return accounts.stream().filter(a->a.getLogin().equals(login)).findFirst();
    }

    public  Optional<Account> findByToken(String token) {
        return accounts.stream().filter(a->a.getToken().equals(token)).findFirst();
    }

    @Override
    public String addUser(String login) throws Exception {
        Account account;
        var foundAccount = findByLogin(login);
        if (foundAccount.isPresent()) {
            account = foundAccount.get();
            account.setToken(UUID.randomUUID().toString());
        }
        else {
            account = new Account(accounts.size() + 1L, login, "Jhon Doe", UUID.randomUUID().toString(),
                    new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority("USER"))));
            if (!accounts.add(account)) throw new IllegalArgumentException();
            log.debug(String.format("Added '%s' with token '%s'", login, account.getToken()));
            log.debug(String.format("Amount users %d.", accounts.size()));
        }
        return account.getToken();
    }

    @Override
    public void updateUser(Long id, String login, String name) throws Exception {
        var foundAccount = accounts.stream().filter(a->a.getId() == id).findFirst();
        if (!foundAccount.isPresent()) throw new IllegalArgumentException(String.format("User with id=%d not found.", id));
        if (null == login && null == name)
            throw new IllegalArgumentException("Login and name cannot are null.");
        var account = foundAccount.get();
        if (null != name && !name.isEmpty()) {
            account.setName(name);
        }
        if (null != login) {
            if (accounts.stream().anyMatch(a->a.getLogin().equals(login))) {
                throw new IllegalArgumentException(String.format("Login '%s' is exists.", login));
            }
            account.setLogin(login);
        }
    }

    @Override
    public List<Account> getAllUsers() throws Exception {
        return accounts.stream().collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws Exception {
        accounts.removeIf(a->a.getId() == id);
    }
}
