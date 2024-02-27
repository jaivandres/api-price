/* (C) Copyright 2024 Frubana. */
package com.frubana.ops.sac.backoffice.customer.application;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frubana.ops.sac.backoffice.common.utils.Done;
import com.frubana.ops.sac.backoffice.common.utils.LoggerUtil;
import com.frubana.ops.sac.backoffice.customer.application.dto.CustomerRequestDTO;
import com.frubana.ops.sac.backoffice.customer.domain.Customer;
import com.frubana.ops.sac.backoffice.customer.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final LoggerUtil loggerUtil;
  private final CustomerService customerService;

  public CustomerController(
      LoggerUtil loggerUtil,
      CustomerService customerService) {
    this.loggerUtil = loggerUtil;
    this.customerService = customerService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Customer post(@Valid @RequestBody CustomerRequestDTO body) {
    loggerUtil.info(String.format("Create customer by id: %s", body.id()));
    return customerService.create(body.toCustomer());
  }

  @PostMapping("/bulk-create")
  public Done createCustomers(@Valid @RequestBody List<CustomerRequestDTO> customers) {
    loggerUtil.info("Create customers");
    customerService.createCustomers(CustomerRequestDTO.toCustomers(customers));
    return Done.instance;
  }

  @GetMapping("/{id}")
  public Customer get(@PathVariable String id) {
    loggerUtil.info(String.format("Get customer by id: %s", id));
    return customerService.getById(id);
  }

  @DeleteMapping("/{id}")
  public Done delete(@PathVariable String id) {
    loggerUtil.info(String.format("Delete customer by id: %s", id));
    customerService.deleteById(id);
    return Done.instance;
  }

}
