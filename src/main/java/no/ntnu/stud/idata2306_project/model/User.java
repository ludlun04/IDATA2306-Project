package no.ntnu.stud.idata2306_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User{
  
  @Id
  Long id;
  String username;
  String password;
}
