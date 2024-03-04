package com.crud.mywebapp.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
//@EnableMethodSecurity(securedEnabled = false, jsr250Enabled = false, prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;
//    @Autowired
//    RememberMeServices rememberMeServices;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
          http
                  .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.GET).permitAll()
                          .requestMatchers(HttpMethod.PUT).permitAll()
                          .requestMatchers(HttpMethod.DELETE).permitAll()
                          .requestMatchers(HttpMethod.POST,"/error").permitAll()
                          .requestMatchers(HttpMethod.DELETE,"/delete").permitAll()
                          .requestMatchers(HttpMethod.POST,"/delete").permitAll()
                          .requestMatchers(HttpMethod.POST,"/saveuser").permitAll()
                          .requestMatchers(HttpMethod.GET,"/saveuser").permitAll()
                          .requestMatchers(HttpMethod.POST,"/logout").permitAll()
                          .requestMatchers(HttpMethod.PUT,"/logout").permitAll()
                          .requestMatchers(HttpMethod.GET,"/logout").permitAll()
                          .requestMatchers(HttpMethod.DELETE,"/logout").permitAll()

                          .requestMatchers(HttpMethod.POST).permitAll()
//                        .requestMatchers("/save")
//                        .hasAnyAuthority( "[ADMIN]")



                        .anyRequest().permitAll())

//                .logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout").permitAll())
                  .formLogin(form ->

                          Customizer.withDefaults()
                )
                  .cors(cors-> cors.disable())
                  .csrf(csrf-> csrf.disable());
        return  http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setPasswordEncoder(passwordEncoder());
        daoProvider.setUserDetailsService(userDetailsService);
        return  new ProviderManager(daoProvider);
    }
//    @Bean
//    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
//        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
//        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("myKey", userDetailsService, encodingAlgorithm);
//        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
//        return rememberMe;
//    }
}
