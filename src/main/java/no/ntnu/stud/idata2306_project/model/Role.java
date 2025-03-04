package no.ntnu.stud.idata2306_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Role {
  @Id
  private long id;

  @NotNull
  private String name;
}
