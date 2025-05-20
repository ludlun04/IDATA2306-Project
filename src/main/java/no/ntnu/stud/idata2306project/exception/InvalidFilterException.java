package no.ntnu.stud.idata2306project.exception;

/**
 * Exception thrown when a filter is invalid.
 */
public class InvalidFilterException extends RuntimeException {

  /**
   * Constructs a new InvalidFilterException with a filter,
   * given parameter, and reason for the given parameter being invalid.
   *
   * @param filter the name of the filter
   * @param givenParameter the parameter that was given
   * @param reason the reason why the given parameter is invalid
   */
  public InvalidFilterException(String filter, String givenParameter, String reason) {
    super("Filter " + filter + " with parameter " + givenParameter
        + " is invalid. Reason: " + reason + ".");
  }
}
