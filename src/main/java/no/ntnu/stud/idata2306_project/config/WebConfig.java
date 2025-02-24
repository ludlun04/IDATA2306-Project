package no.ntnu.stud.idata2306_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(@NonNull CorsRegistry registry) {

    registry
      .addMapping("/**") //all endpoints
      .allowedOrigins("*") //allow all origins
      .allowedMethods("GET", "POST", "PUT", "DELETE") //allow HTTP request methods
      .allowedHeaders("*") //allow all headers
      .maxAge(3600); //response can be cached by clients for 1 hour
  }
}