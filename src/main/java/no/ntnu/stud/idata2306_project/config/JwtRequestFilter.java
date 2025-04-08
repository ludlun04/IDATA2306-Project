package no.ntnu.stud.idata2306_project.config;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;

import no.ntnu.stud.idata2306_project.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Filter class for handling JWT tokens.
 * Inspiration taken from:
 * girt
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {


  UserDetailsService userDetailsService;
  JwtUtil jwtUtil;
  private Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

  public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
    super();
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
        String jwtToken = getJwtToken(request);
        try {
          String username = jwtToken != null ? getUsernameFromToken(jwtToken) : null;

          if (username != null && !isContextAuthenticated()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null && jwtUtil.validateToken(jwtToken, userDetails)) {
              registerUserAsAuthenticated(request, userDetails);
            }
          }
        } catch (ExpiredJwtException e) {
          logger.error("JWT token is expired: {}", e.getMessage());
        }

      filterChain.doFilter(request, response);
  }

  private String getJwtToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    String jwt = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.replace("Bearer ", "");
    }
    return jwt;
  }

  private String getUsernameFromToken(String jwtToken) {
    return jwtUtil.extractUsername(jwtToken);
  }

  private boolean isContextAuthenticated() {
    return SecurityContextHolder.getContext().getAuthentication() != null;
  }

  private static void registerUserAsAuthenticated(HttpServletRequest request,
                                                  UserDetails userDetails) {
    final UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(upat);
  }
  
}
