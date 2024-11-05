package com.ama.agencybooks.config;

import com.ama.agencybooks.service.BookService;
import com.ama.agencybooks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.ama.agencybooks.model.SecurityLevel.TOP_SECRET;
import static com.ama.agencybooks.model.SecurityLevel.UNCLASSIFIED;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Value("${usr.top.secret}")
    private String userTopSecret;
    @Value("${pass.top.secret}")
    private String passTopSecret;
    @Value("${usr.unclassified}")
    private String userUnclassified;
    @Value("${pass.unclassified}")
    private String passUnclassified;

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/public/**")
                        .permitAll())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/books/unclassified").hasRole(UNCLASSIFIED.name())
                        .requestMatchers("/books/top_secret").hasRole(TOP_SECRET.name())
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    protected InMemoryUserDetailsManager userConfig() {
        UserDetails unclassifiedDetails = User.builder()
                .username(userUnclassified)
                .password(encoder().encode(passUnclassified))
                .roles(UNCLASSIFIED.name())
                .build();

        UserDetails topSecretDetails = User.builder()
                .username(userTopSecret)
                .password(encoder().encode(passTopSecret))
                .roles(TOP_SECRET.name())
                .build();

        return new InMemoryUserDetailsManager(unclassifiedDetails, topSecretDetails);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
