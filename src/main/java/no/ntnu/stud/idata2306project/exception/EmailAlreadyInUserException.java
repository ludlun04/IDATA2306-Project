package no.ntnu.stud.idata2306project.exception;

/**
 * Exception thrown when a user with the same email already exists.
 */
public class EmailAlreadyInUserException extends RuntimeException {

  /**
   * Constructs a new EmailAlreadyInUserException with the specified detail message.
   *
   * @param email the email of the user
   */
  public EmailAlreadyInUserException(String email) {
    super("User with email " + email + " already exists");
  }

  /**
   * Constructs a new EmailAlreadyInUserException with the specified detail message and cause.
   *
   * @param email the email of the user
   * @param cause the cause of the exception
   */
  public EmailAlreadyInUserException(String email, Throwable cause) {
    super("User with email " + email + " already exists", cause);
  }
}
