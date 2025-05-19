package no.ntnu.stud.idata2306project.exception;

/**
 * Exception thrown when a filter parameter is missing.
 */
public class MissingFilterParameterException extends InvalidFilterException {

  /**
   * Constructs a new MissingFilterParameterException with a filter, and a given parameter.
   *
   * @param filter the name of the filter
   * @param givenParameter the parameter that was given
   */
  public MissingFilterParameterException(String filter, String givenParameter) {
    super(filter, givenParameter, "Missing a filter parameter");
  }
}
