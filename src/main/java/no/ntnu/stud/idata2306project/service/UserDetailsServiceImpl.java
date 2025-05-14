package no.ntnu.stud.idata2306project.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import no.ntnu.stud.idata2306project.model.user.User;
import no.ntnu.stud.idata2306project.repository.UserRepository;
import no.ntnu.stud.idata2306project.security.AccessUserDetails;

import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  /**
   * The user repository to use.
   */
  UserRepository userRepository;

  /**
   * Logger for this class.
   */
  Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  /**
   * Creates a new UserDetailsServiceImpl.
   *
   * @param userRepository the user repository to use
   */
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Loads a user by email.
   *
   * @param email the email of the user to load
   * @return the user details
   * @throws UsernameNotFoundException if the user is not found
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    logger.info("Loading user by email: {}", email);
    Optional<User> user = userRepository.findByEmail(email);
    
    if (user.isEmpty()) {
      logger.error("User not found: {}", email);
      throw new UsernameNotFoundException("User not found");
    }

    logger.info("User found: {}", email);
    return new AccessUserDetails(user.get());
  }
}
