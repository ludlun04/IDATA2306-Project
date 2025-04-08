package no.ntnu.stud.idata2306_project.exception;

public class UsernameAlreadyInUser extends RuntimeException {
    public UsernameAlreadyInUser(String username) {
        super("User with username " + username + " already exists");
    }
    public UsernameAlreadyInUser(String username, Throwable cause) {
        super("User with username " + username + " already exists", cause);
    }
}
