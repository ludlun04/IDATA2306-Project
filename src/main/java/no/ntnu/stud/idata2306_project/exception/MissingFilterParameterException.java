package no.ntnu.stud.idata2306_project.exception;

public class MissingFilterParameterException extends RuntimeException {
  public MissingFilterParameterException(String filter, String givenParameter) {
    super("Filter " + filter + " with parameter " + givenParameter + " is missing a required parameter");
  }
}
