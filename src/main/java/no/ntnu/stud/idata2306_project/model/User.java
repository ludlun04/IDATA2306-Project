package no.ntnu.stud.idata2306_project.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;
import no.ntnu.stud.idata2306_project.enums.Gender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User entity class
 * 
 * <p> Will be used for possible authentication
 */
@Entity
@Table(name = "app_user")
public class User implements UserDetails {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false, updatable = false)
  Long id;

  @NotNull
  @Column(unique = true)
  String username;

  @NotNull
  String password;

  //TODO: First/Last name

  @NotNull
  int phoneNumber;

  @NotNull
  Date dateOfBirth;
  String email;
  Gender gender = Gender.Unidentified;


  public User() {}

  public User(String username, String password, int phoneNumber, Date dateOfBirth, String email, Gender gender) {
    super();

    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }

    if (password == null || password.isEmpty()) {
      throw new IllegalArgumentException("Password cannot be null or empty");
    }

    if (phoneNumber < 0 || phoneNumber > 99999999) {
      throw new IllegalArgumentException("Phone number cannot be negative");
    }

    if (email != null) {
      if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email)) {
          throw new IllegalArgumentException("Email is not valid");
      }
    }

    if (dateOfBirth == null) {
      throw new IllegalArgumentException("Date of birth cannot be null");
    }

    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = dateOfBirth;
    this.email = email;
    this.gender = gender;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();

    authorities.add(new SimpleGrantedAuthority("ADMIN"));

    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public int getPhoneNumber() {
      return this.phoneNumber;
  }

  public Date getDateOfBirth() {
      return this.dateOfBirth;
  }

  public String getEmail() {
      return this.email;
  }

  public Gender getGender() {
      return this.gender;
  }
}
