package no.ntnu.stud.idata2306project.exception;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends RuntimeException {

  /**
   * Constructs a new UserNotFoundException with the specified detail message.
   *
   * @param message the detail message
   */
  public UserNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs a new UserNotFoundException with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause   the cause of the exception
   */
  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

}
