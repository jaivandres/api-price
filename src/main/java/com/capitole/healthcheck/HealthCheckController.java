/* (C) Copyright 2024 Frubana. */
package com.frubana.ops.sac.backoffice.healthcheck;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

  @GetMapping
  public Map<String, String> health() {
    return Map.of("status", "UP");
  }

}