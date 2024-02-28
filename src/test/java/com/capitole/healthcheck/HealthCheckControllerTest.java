/* (C) Copyright 2024 Capitole. */
package com.capitole.healthcheck;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.capitole.price.Application;


@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class HealthCheckControllerTest {

  private static final String HEALTH_CHECK_PATH = "/health-check";

  @Autowired
  private MockMvc mockMvc;

  @Test
  void testVerifyHealthCheckController() throws Exception {
    mockMvc.perform(get(HEALTH_CHECK_PATH)
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("status", is("UP")));
  }

}
