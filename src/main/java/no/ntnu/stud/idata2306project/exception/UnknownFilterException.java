package no.ntnu.stud.idata2306project.exception;

/**
 * Exception thrown when an unknown filter is used.
 */
public class UnknownFilterException extends InvalidFilterException {

  /**
   * Constructs a new UnknownFilterException with a filter, and a given parameter.
   *
   * @param filter the name of the filter
   * @param givenParameter the parameter that was given
   */
  public UnknownFilterException(String filter, String givenParameter) {
    super(filter, givenParameter, "Unknown filter");
  }
}
