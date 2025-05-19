package no.ntnu.stud.idata2306project.exception;

/**
 * Exception thrown when a user is not authorized to perform an action.
 */
public class UnauthorizedException extends RuntimeException {

  /**
   * Constructs a new UnauthorizedException with the specified detail message.
   *
   * @param message the detail message
   */
  public UnauthorizedException(String message) {
    super(message);
  }
}
