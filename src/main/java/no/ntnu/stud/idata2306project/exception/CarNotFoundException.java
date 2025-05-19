package no.ntnu.stud.idata2306project.exception;

/**
 * Exception thrown when a car is not found.
 */
public class CarNotFoundException extends RuntimeException {

  /**
   * Constructs a new CarNotFoundException with the specified detail message.
   *
   * @param message the detail message
   */
  public CarNotFoundException(String message) {
    super(message);
  }
}
