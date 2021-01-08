package org.example.repositories;

import org.example.entities.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MyRepository {
    Optional<Account> findByLogin(String login);
    Optional<Account> findByToken(String token);
    String addUser(String login) throws Exception ;
    void updateUser(Long id, String login, String name) throws Exception;
    List<Account> getAllUsers() throws Exception;
    void delete(Long id) throws Exception;

}
