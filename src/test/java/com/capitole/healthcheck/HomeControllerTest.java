/* (C) Copyright 2024 Capitole. */
package com.capitole.healthcheck;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.capitole.price.application.HomeController;

@ExtendWith(SpringExtension.class)
class HomeControllerTest {

  @Test
  void testVerifyHomeController() {
    assertThat(new HomeController().index(), containsString("swagger-ui"));
  }

}
