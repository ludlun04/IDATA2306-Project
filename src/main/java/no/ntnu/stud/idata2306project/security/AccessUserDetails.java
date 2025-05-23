package no.ntnu.stud.idata2306project.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import no.ntnu.stud.idata2306project.model.user.Role;
import no.ntnu.stud.idata2306project.model.user.User;

/**
 * Class representing the user details for security
 */
public class AccessUserDetails implements UserDetails {
  private String email;
  private String password;
  private Long userId;
  private Set<GrantedAuthority> authorities;

  /**
   * Constructor for AccessUserDetails
   * 
   * @param user The user to create the details for
   */
  public AccessUserDetails(User user) {
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.userId = user.getId();
    this.authorities = new HashSet<>();
    convertRolesToAuthorities(user.getRoles());
  }

  private void convertRolesToAuthorities(Set<Role> roles) {
    Iterator<Role> iterator = roles.iterator();

    while (iterator.hasNext()) {
      Role role = iterator.next();
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
      this.authorities.add(authority);
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  /**
   * Returns the user's id
   *
   * @return The user's id
   */
  public Long getId() {
    return this.userId;
  }

  /**
   * Returns true if the user is admin, false otherwise
   *
   * @return True if the user is admin, false otherwise
   */
  public boolean isAdmin() {
    return this.getAuthorities().stream()
        .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
  }
}