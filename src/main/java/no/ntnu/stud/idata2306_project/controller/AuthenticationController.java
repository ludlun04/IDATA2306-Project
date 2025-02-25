package no.ntnu.stud.idata2306_project.controller;

import no.ntnu.stud.idata2306_project.config.AuthenticationRequest;
import no.ntnu.stud.idata2306_project.config.JwtUtil;
import no.ntnu.stud.idata2306_project.service.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
  private AuthenticationManager authenticationManager;
  private UserDetailsServiceImpl userDetailsService;
  private JwtUtil jwtUtil;

  public AuthenticationController(
    AuthenticationManager authenticationManager,
    UserDetailsServiceImpl userDetailsService,
    JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
      ));
    } catch (BadCredentialsException e) {
      return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
    }
    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(jwt);
  }
}
