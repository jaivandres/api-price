/* (C) Copyright 2024 Capitole. */
package com.capitole.price.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capitole.price.domain.Price;
import com.capitole.price.repository.PriceRepository;

@Service
public class PriceService {

  private final PriceRepository repository;

  public PriceService(PriceRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public List<Price> find(LocalDateTime applicationDate, Integer productId, String brandId) {
    return repository.find(applicationDate, productId, brandId);
  }

  @Transactional(readOnly = true)
  public Price getById(Integer id) {
    return repository.getById(id);
  }

  @Transactional
  public void deleteById(Integer id) {
    repository.deleteById(id);
  }

  @Transactional
  public Price create(Price price) {
    return repository.save(price);
  }


}
