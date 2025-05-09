package no.ntnu.stud.idata2306project.exception;

public class MissingFilterParameterException extends InvalidFilterException {
  public MissingFilterParameterException(String filter, String givenParameter) {
    super(filter, givenParameter, "Missing a filter parameter");
  }
}
