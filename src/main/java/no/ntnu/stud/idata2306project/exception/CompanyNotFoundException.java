package no.ntnu.stud.idata2306project.exception;

/**
 * Exception thrown when a company is not found.
 */
public class CompanyNotFoundException extends RuntimeException {

  /**
   * Constructs a new CompanyNotFoundException with the specified detail message.
   *
   * @param message the detail message
   */
  public CompanyNotFoundException(String message) {
    super(message);
  }
}
