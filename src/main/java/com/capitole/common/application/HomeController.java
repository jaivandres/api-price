/* (C) Copyright 2024 Frubana. */
package com.capitole.price.common.application;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden
public class HomeController {

  static final String REDIRECT_SWAGGER_UI_HTML = "redirect:swagger-ui.html";

  @GetMapping(value = "/")
  public String index() {
    return REDIRECT_SWAGGER_UI_HTML;
  }

}
