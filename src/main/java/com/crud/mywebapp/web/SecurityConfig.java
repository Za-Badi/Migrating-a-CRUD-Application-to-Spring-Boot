package com.crud.mywebapp.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {
          http
                  .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/error").permitAll()
                          .requestMatchers("/error").permitAll()
                          .requestMatchers(HttpMethod.DELETE,"/delete").hasAuthority("ADMIN")
                          .requestMatchers(HttpMethod.POST,"/delete").hasAuthority("ADMIN")
                          .requestMatchers(HttpMethod.POST,"/saveuser").hasAuthority("ADMIN")
                          .requestMatchers(HttpMethod.GET,"/saveuser").hasAuthority("ADMIN")
                          .requestMatchers(HttpMethod.POST,"/logout").authenticated()
                          .requestMatchers(HttpMethod.PUT,"/logout").permitAll()
                          .requestMatchers(HttpMethod.GET,"/logout").permitAll()
                          .anyRequest().authenticated())
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
