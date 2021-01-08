package org.example;

import lombok.extern.log4j.Log4j;
import org.example.repositories.AccountRepository;
import org.example.repositories.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
        //(scanBasePackages = {"org.example.config", "org.example.repositories", "org.example.security"})
@ComponentScan("org.example")

@Log4j
public class App {
//    @Autowired(required = true)
//    @Qualifier("myrepo")
    public static  MyRepository repo = new AccountRepository();

    public static void main(String[] args) {
        log.debug("Start App.");
        SpringApplication.run(App.class, args);
        if (null == repo) {
            log.error("Repo at the App file is NULL!");
        }
    }

//    @Bean(name = "myrepo")
//    MyRepository createService() {
//        log.debug("CreateService");
//        return  new AccountRepository();
//    }

//    @Bean(name = "myrepo")
//    public MyRepository createRepository() {
//        return new AccountRepository();
//    }
}
