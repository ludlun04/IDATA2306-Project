package no.ntnu.stud.idata2306_project.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import no.ntnu.stud.idata2306_project.model.user.Role;
import no.ntnu.stud.idata2306_project.model.user.User;

/**
 * Class representing the user details for security
 */
public class AccessUserDetails implements UserDetails {
  private String username;
  private String password;
  private ArrayList<GrantedAuthority> authorities;

  /**
   * Constructor for AccessUserDetails
   * 
   * @param user The user to create the details for
   */
  public AccessUserDetails(User user) {
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.authorities = new ArrayList<>();
    convertRolesToAuthorities(user.getRoles());
  }

  private void convertRolesToAuthorities(List<Role> roles) {
    for (Role role : roles) {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
      authorities.add(authority);
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
    return this.username;
  }
  
}
