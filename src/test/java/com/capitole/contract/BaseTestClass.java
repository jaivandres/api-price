/* (C) Copyright 2024 Capitole. */
package com.capitole.contract;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import com.capitole.price.Application;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(classes = Application.class)
public abstract class BaseTestClass {

  @Autowired
  protected WebApplicationContext context;

  private static final Logger log = LoggerFactory.getLogger(BaseTestClass.class);

  @BeforeEach
  void setup(TestInfo testInfo) throws IOException {
    log.info("Running: " + testInfo.getDisplayName());
    RestAssuredMockMvc.webAppContextSetup(this.context);
  }

  @AfterEach
  public void tearDown() throws IOException {}

}
