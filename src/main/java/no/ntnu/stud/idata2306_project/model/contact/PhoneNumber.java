package no.ntnu.stud.idata2306_project.model.contact;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PhoneNumber implements Serializable {
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
