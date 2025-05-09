package no.ntnu.stud.idata2306project.exception;

public class UnknownFilterException extends InvalidFilterException {
  public UnknownFilterException(String filter, String givenParameter) {
    super(filter, givenParameter, "Unknown filter");
  }
}
