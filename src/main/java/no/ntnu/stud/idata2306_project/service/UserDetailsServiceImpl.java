package no.ntnu.stud.idata2306_project.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import no.ntnu.stud.idata2306_project.model.user.User;
import no.ntnu.stud.idata2306_project.repository.UserRepository;
import no.ntnu.stud.idata2306_project.security.AccessUserDetails;

import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  UserRepository userRepository;

  Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("Loading user by username: {}", username);
    Optional<User> user = userRepository.findByUsername(username);
    
    if (user.isEmpty()) {
      logger.error("User not found: {}", username);
      throw new UsernameNotFoundException("User not found");
    }

    logger.info("User found: {}", username);
    return new AccessUserDetails(user.get());
  }
}
