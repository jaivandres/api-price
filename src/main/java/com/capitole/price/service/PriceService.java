/* (C) Copyright 2024 Frubana. */
package com.frubana.ops.sac.backoffice.customer.service;

import static com.frubana.ops.sac.backoffice.common.exception.ErrorCode.CUSTOMER_ALREADY_EXISTS;
import static com.frubana.ops.sac.backoffice.common.exception.ErrorCode.CUSTOMER_NOT_FOUND;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frubana.ops.sac.backoffice.common.exception.BusinessException;
import com.frubana.ops.sac.backoffice.common.utils.LoggerUtil;
import com.frubana.ops.sac.backoffice.customer.domain.Customer;
import com.frubana.ops.sac.backoffice.customer.repository.CustomerRepository;

@Service
public class CustomerService {

  private final LoggerUtil loggerUtil;
  private final CustomerRepository repository;

  public CustomerService(
      LoggerUtil loggerUtil, CustomerRepository repository) {
    this.loggerUtil = loggerUtil;
    this.repository = repository;
  }

  @Transactional
  public Customer create(Customer customer) {
    loggerUtil.info(String.format("Create customer by id: %s", customer.getId()));
    repository.findById(customer.getId()).ifPresent(c -> {
      throw new BusinessException(CUSTOMER_ALREADY_EXISTS);
    });
    return repository.create(customer);
  }

  @Async
  public void createCustomers(List<Customer> customers) {
    customers.forEach(customer -> {
      try {
        this.create(customer);
      } catch (Exception e) {
        loggerUtil.info(String.format("Error creating customer by id: %s", customer.getId()));
      }
    });
  }

  @Transactional(readOnly = true)
  public Customer getById(String id) {
    loggerUtil.info(String.format("Get customer by id: %s", id));
    return repository.findById(id)
        .orElseThrow(() -> new BusinessException(CUSTOMER_NOT_FOUND));
  }

  @Transactional
  public void deleteById(String id) {
    loggerUtil.info(String.format("Delete customer by id: %s", id));
    getById(id);
    repository.delete(id);
  }

}
