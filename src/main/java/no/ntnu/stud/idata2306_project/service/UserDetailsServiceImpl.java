package no.ntnu.stud.idata2306_project.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import no.ntnu.stud.idata2306_project.model.User;
import no.ntnu.stud.idata2306_project.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);

    if (user.isEmpty()) {
      
      throw new UsernameNotFoundException("User not found");
    }
    
    return user.get();
  }
}
