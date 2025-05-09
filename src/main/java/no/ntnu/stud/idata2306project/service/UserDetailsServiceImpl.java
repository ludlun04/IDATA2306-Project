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

  UserRepository userRepository;

  Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

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
