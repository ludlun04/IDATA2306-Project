package no.ntnu.stud.idata2306_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PhoneNumber {
  @Id
  private Long id;

  public PhoneNumber() {}

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
