package no.ntnu.stud.idata2306project.config;

/**
 * Represents an authentication request.
 *
 * <p>Consists of a username and a password</p>
 */
public class AuthenticationRequest {
  private String username;
  private String password;

  /**
   * Create an authentication request using a given username and password.
   *
   * @param username the given username
   * @param password the given password
   */
  public AuthenticationRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * Set the password of the authentication request.
   *
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Get the password of the authentication request.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set the username of the authentication request.
   *
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Get the username of the authentication request.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }
}
