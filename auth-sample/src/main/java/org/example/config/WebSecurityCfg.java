package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
//    }

//    @Autowired
//    MyUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
        .formLogin().disable()
        .csrf().disable()
        .sessionManagement().disable()
//       .authorizeRequests().antMatchers("/**").permitAll().and()
//        .authorizeRequests().antMatchers("/sign-in").permitAll().and()
     //   .authorizeRequests().anyRequest().authenticated()
//        .and().httpBasic()
//           .addFilter(new AuthTokenFilter()).authorizeRequests().anyRequest().authenticated()
//               .addFilter(new AuthProcessingFilter()).authorizeRequests().anyRequest().authenticated()
//          .addFilterBefore(new AuthProcessingFilter(), AuthProcessingFilter.class).authorizeRequests()
           .addFilterBefore(new AuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);//.authorizeRequests().anyRequest().authenticated();
    }



//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new MyUserDetailsService();
//    }
}
