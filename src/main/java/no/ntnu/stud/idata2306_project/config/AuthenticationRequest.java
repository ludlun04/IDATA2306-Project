package no.ntnu.stud.idata2306_project.config;

public class AuthenticationRequest {
  private String username;
  private String password;

  public AuthenticationRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getUsername() {
    return username;
  }
}
