/* (C) Copyright 2024 Capitole. */
package com.capitole.price.service;

import static com.capitole.price.exception.ErrorCode.PRICE_ALREADY_EXISTS;
import static com.capitole.price.exception.ErrorCode.PRICE_NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capitole.price.domain.Price;
import com.capitole.price.exception.BusinessException;
import com.capitole.price.repository.PriceRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
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
    return repository.findById(id).orElseThrow(() -> new BusinessException(PRICE_NOT_FOUND));
  }

  @Transactional
  public void deleteById(Integer id) {
    log.info(String.format("Delete price by id: %s", id));
    getById(id);
    repository.deleteById(id);
  }

  @Transactional
  public Price create(Price price) {
    repository.findById(price.getId()).ifPresent(c -> {
      throw new BusinessException(PRICE_ALREADY_EXISTS);
    });
    return repository.save(price);
  }

}
