package no.ntnu.stud.idata2306_project.model.contact;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public Address() { // default

  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
