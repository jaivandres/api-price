/* (C) Copyright 2024 Capitole. */
package com.capitole.price.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capitole.price.domain.Price;
import com.capitole.price.service.PriceService;
import com.capitole.price.utils.Done;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/prices")
@Log4j2
public class PriceController {

  private final PriceService service;

  public PriceController(PriceService service) {
    this.service = service;
  }

  /** Method to find prices based on application date, product ID, and brand ID.
   *
   * @param applicationDate Date and time of application in ISO 8601 format (YYYY-MM-DDTHH:MM:SS)
   * @param productId ID of the product
   * @param brandId ID of the brand
   * @return List of prices corresponding to the provided parameters */
  @GetMapping
  public List<Price> find(
      @RequestParam
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
      @RequestParam Integer productId,
      @RequestParam Integer brandId) {
    log.info(String.format("Find prices: %s %s %s", applicationDate, productId, brandId));
    return service.find(applicationDate, productId, brandId);
  }

  /** Handles HTTP POST requests to create a new Price.
   *
   * @param body The Price object to be created. Must be a valid JSON representation of the Price
   *        entity.
   * @return The created Price object. */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Price post(@Valid @RequestBody Price body) {
    log.info(String.format("Create price by id: %s", body.getPrice()));
    return service.create(body);
  }

  @PutMapping
  public Price put(@Valid @RequestBody Price body) {
    log.info(String.format("Update price by id: %s", body.getPrice()));
    return service.create(body);
  }

  /** Handles HTTP DELETE requests to delete a Price by its ID.
   *
   * @param id The ID of the Price to delete.
   * @return A Done object indicating successful deletion. */
  @DeleteMapping("/{id}")
  public Done delete(@PathVariable int id) {
    log.info(String.format("Delete price by id: %s", id));
    service.deleteById(id);
    return Done.instance;
  }

  /** Handles HTTP GET requests to retrieve a Price by its ID.
   *
   * @param id The ID of the Price to retrieve.
   * @return The Price object with the specified ID. */
  @GetMapping("/{id}")
  public Price get(@PathVariable int id) {
    log.info(String.format("Get price by id: %s", id));
    return service.getById(id);
  }

}
