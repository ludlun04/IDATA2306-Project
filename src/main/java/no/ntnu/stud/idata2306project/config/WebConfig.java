package no.ntnu.stud.idata2306project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for CORS settings.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  /**
   * Configures CORS settings for the application.
   *
   * <p>Allows all origins, methods, and headers. The response can be cached by clients for 1 hour.
   *
   * @param registry the CORS registry to configure
   */
  @Override
  public void addCorsMappings(@NonNull CorsRegistry registry) {

    registry
        .addMapping("/**") //all endpoints
        .allowedOrigins("*") //allow all origins
        .allowedMethods("GET", "POST", "PUT", "DELETE") //allow HTTP request methods
        .allowedHeaders("*") //allow all headers
        .maxAge(3600 * 3L); //response can be cached by clients for 3 hours
  }
}