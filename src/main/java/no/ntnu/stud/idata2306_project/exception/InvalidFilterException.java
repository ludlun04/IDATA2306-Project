package no.ntnu.stud.idata2306_project.exception;

public class InvalidFilterException extends RuntimeException {
  public InvalidFilterException(String filter, String givenParameter, String reason) {
    super("Filter " + filter + " with parameter " + givenParameter + " is invalid. Reason: " + reason + ".");
  }
}
