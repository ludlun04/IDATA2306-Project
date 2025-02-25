package no.ntnu.stud.idata2306_project.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class DefaultController {

  @GetMapping("/")
  public String getRoot() {
    return "Hello World!";
  }

}
