package no.ntnu.stud.idata2306project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.stud.idata2306project.config.AuthenticationRequest;
import no.ntnu.stud.idata2306project.config.JwtUtil;
import no.ntnu.stud.idata2306project.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents a controller for authentication.
 *
 * <p>Contains the following endpoints:
 * <ul>
 *   <li> Authenticate user
 *   <li> Validate Jwt
 * </ul>
 */
@Tag(name = "Authentication", description = "Endpoints for authentication")
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;
  private final JwtUtil jwtUtil;

  Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

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
  @Operation(summary = "Authenticate user",
      description = "Authenticates a user and returns a JWT token")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "JWT"),
      @ApiResponse(responseCode = "400", description = "Missing body"),
      @ApiResponse(responseCode = "401", description = "Incorrect username or password"),
      @ApiResponse(responseCode = "500", description = "Error during authentication")
  })
  @PostMapping()
  public ResponseEntity<String> authenticate(
      @RequestBody(required = false) AuthenticationRequest request) {
    if (request == null) {
      logger.warn("Missing body in authenticate request");
      return new ResponseEntity<>("Missing body", HttpStatus.BAD_REQUEST);
    }

    logger.info("Authenticating user {}", request.getUsername());

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          request.getUsername(),
          request.getPassword()));

      UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
      String jwt = jwtUtil.generateToken(userDetails);
      return ResponseEntity.ok(jwt);
    } catch (BadCredentialsException e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
    } catch (UsernameNotFoundException e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>("Error during authentication", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Validates the JWT token.
   *
   * @return a response entity containing a message indicating whether the token is valid
   */
  @Operation(summary = "Validate JWT token", description = "Validates the JWT token")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Token is valid"),
      @ApiResponse(responseCode = "403", description = "Token is invalid")
  })
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @GetMapping("/validate")
  public ResponseEntity<String> validateJwt() {
    return ResponseEntity.ok("Token is valid");
  }
}
