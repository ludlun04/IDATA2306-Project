package no.ntnu.stud.idata2306_project.exception;

public class EmailAlreadyInUser extends RuntimeException {
  public EmailAlreadyInUser(String email) {
    super("User with email " + email + " already exists");
  }

  public EmailAlreadyInUser(String email, Throwable cause) {
    super("User with email " + email + " already exists", cause);
  }
}
