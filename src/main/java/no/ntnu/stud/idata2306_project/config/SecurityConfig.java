package no.ntnu.stud.idata2306_project.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

  JwtRequestFilter jwtRequestFilter;

  public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
    super();
    this.jwtRequestFilter = jwtRequestFilter;
  }

  /**
   * Configures the security filter chain.
   *
   * @param http the HttpSecurity object
   * @return the SecurityFilterChain object
   * @throws Exception when security configuration fails
   */
  @Bean
  public SecurityFilterChain configureAuthorizationFilterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(withDefaults())
      .authorizeHttpRequests(auth -> auth
        // Allow only GET requests
        .requestMatchers(
          HttpMethod.GET,
          "/car",
          "/car/{id}",
          "/company/with_rentals",
          "/brand/with_rentals",
          "/fuel/with_rentals",
          "/image/{carId}/{imageType}/{imageWidth}",
          "/order/car/{carId}",
          "company/cars/{companyId}"
        ).permitAll()
        // Allow all requests
        .requestMatchers(
          "/users/add",
          "/",
          "/authenticate",
          "/v3/api-docs/**",
          "/swagger-ui/**"
        ).permitAll()

        .anyRequest().authenticated())
      .addFilterBefore(this.jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

      .formLogin(AbstractHttpConfigurer::disable)
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
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
