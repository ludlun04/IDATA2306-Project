package no.ntnu.stud.idata2306project.exception;

/**
 * Exception thrown when the given dates are invalid.
 */
public class InvalidDatesException extends RuntimeException {

  /**
   * Constructs a new InvalidDatesException with the specified detail message.
   *
   * @param message the detail message
   */
  public InvalidDatesException(String message) {
    super(message);
  }
}
