package no.ntnu.stud.idata2306project.exception;

/**
 * Exception thrown when a user with the given username already exists.
 */
public class UsernameAlreadyInUserException extends RuntimeException {

  /**
   * Constructs a new UsernameAlreadyInUserException with the specified detail message.
   *
   * @param username the username of the user
   */
  public UsernameAlreadyInUserException(String username) {
    super("User with username " + username + " already exists");
  }

  /**
   * Constructs a new UsernameAlreadyInUserException with the specified detail message and cause.
   *
   * @param username the username of the user
   * @param cause    the cause of the exception
   */
  public UsernameAlreadyInUserException(String username, Throwable cause) {
    super("User with username " + username + " already exists", cause);
  }
}
