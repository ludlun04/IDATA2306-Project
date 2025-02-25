package no.ntnu.stud.idata2306_project.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import no.ntnu.stud.idata2306_project.model.User;
import no.ntnu.stud.idata2306_project.repository.UserRepository;

/**
 * Config class for security.
 * Inspiration taken from:
 * https://github.com/strazdinsg/app-dev/tree/main/security-demos/05-jwt-authentication
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  /**
   * Configures the security filter chain.
   *
   * @param http the HttpSecurity object
   * @throws Exception when security configuration fails
   * @return the SecurityFilterChain object
   */
  @Bean
  public SecurityFilterChain configureAuthorizationFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
          .requestMatchers("/authenticate").permitAll()
          .requestMatchers("/").permitAll()
          .anyRequest().authenticated()
        )
        .formLogin(withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }

  /**
   * Returns the password encoder.
   *
   * @return the password encoder
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    return passwordEncoder;
  }

  @Bean
  CommandLineRunner createInitialUsers(UserRepository userRepository) {
    return args -> {
      Optional<User> optional = userRepository.findByUsername("user");

      if (optional.isEmpty()) {
        User user = new User("user", passwordEncoder.encode("password"));

        System.out.println(user.getAuthorities());
        userRepository.save(user);
      }
    };
  }
}
