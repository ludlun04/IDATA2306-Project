package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

/**
 * Represents a controller for authentication.
 *
 * <p>Contains the endpoint used for authenticating users.
 */
@RestController
public class AuthenticationController {
  private AuthenticationManager authenticationManager;
  private UserDetailsServiceImpl userDetailsService;
  private JwtUtil jwtUtil;

  /**
   * Creates a new AuthenticationController.
   *
   * @param authenticationManager the authentication manager to use
   * @param userDetailsService    the user details service to use
   * @param jwtUtil               the JWT utility to use
   */
  public AuthenticationController(
      AuthenticationManager authenticationManager,
      UserDetailsServiceImpl userDetailsService,
      JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  /**
   * Authenticates a user.
   *
   * @param request the authentication request
   * @return a response entity containing the JWT token
   */
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "JWT"),
      @ApiResponse(responseCode = "400", description = "Missing body"),
      @ApiResponse(responseCode = "401", description = "Incorrect username or password")
  })
  @PostMapping("/authenticate")
  public ResponseEntity<String> authenticate(@RequestBody(required = false) AuthenticationRequest request) {
    if (request == null) {
      return new ResponseEntity<>("Missing body", HttpStatus.BAD_REQUEST);
    }
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
