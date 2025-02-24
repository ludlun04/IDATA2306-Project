package no.ntnu.stud.idata2306_project.model;

import jakarta.persistence.*;

/**
 * User entity class
 * 
 * <p> Will be used for possible authenticaion
 */
@Entity
@Table(name = "app_user")
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false, updatable = false)
  Long id;
  String username;
  String password;
}
