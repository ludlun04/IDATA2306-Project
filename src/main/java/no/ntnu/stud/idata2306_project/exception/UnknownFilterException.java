package no.ntnu.stud.idata2306_project.exception;

public class UnknownFilterException extends RuntimeException {
  public UnknownFilterException(String filter) {
    super("No filter found for " + filter);
  }
}
