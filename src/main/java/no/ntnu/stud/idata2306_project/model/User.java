package no.ntnu.stud.idata2306_project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * User entity class
 * 
 * <p> Will be used for possible authenticaion in the future
 */
@Entity
public class User{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false, updatable = false)
  Long id;
  String username;
  String password;
}
