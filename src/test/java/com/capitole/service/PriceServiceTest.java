/* (C) Copyright 2024 Capitole. */
package com.capitole.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capitole.price.domain.Brand;
import com.capitole.price.domain.Price;
import com.capitole.price.exception.BusinessException;
import com.capitole.price.exception.ErrorCode;
import com.capitole.price.repository.PriceRepository;
import com.capitole.price.service.PriceService;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

  private static final Integer PRICE_ID = 1;

  @InjectMocks
  private PriceService service;

  @Mock
  private PriceRepository repository;

  @Test
  void testGetPriceById() {
    var expected = Optional.of(buildPrice());
    doReturn(expected).when(repository).findById(PRICE_ID);
    var response = service.getById(PRICE_ID);
    assertThat(response, is(expected.get()));
  }

  @Test
  void testGetPriceByIdNotFound() {
    BusinessException exception = assertThrows(BusinessException.class,
        () -> service.getById(PRICE_ID));
    assertThat(exception, notNullValue());
    assertThat(exception.getErrorCode(), is(ErrorCode.PRICE_NOT_FOUND));
  }

  @Test
  void testCreatePrice() {
    var expected = buildPrice();
    doReturn(expected).when(repository).save(expected);
    var response = service.create(expected);
    assertThat(response, is(expected));
  }

  @Test
  void testCreatePriceAlreadyExists() {
    var expected = Optional.of(buildPrice());
    doReturn(expected).when(repository).findById(PRICE_ID);
    BusinessException exception = assertThrows(BusinessException.class,
        () -> service.create(expected.get()));
    assertThat(exception, notNullValue());
    assertThat(exception.getErrorCode(), is(ErrorCode.PRICE_ALREADY_EXISTS));
  }

  @Test
  void testDeletePrice() {
    var expected = Optional.of(buildPrice());
    doReturn(expected).when(repository).findById(PRICE_ID);
    doNothing().when(repository).deleteById(PRICE_ID);
    service.deleteById(PRICE_ID);
    verify(repository).deleteById(PRICE_ID);
  }

  @Test
  void testDeletePriceNotFound() {
    var expected = Optional.empty();
    doReturn(expected).when(repository).findById(PRICE_ID);
    BusinessException exception = assertThrows(BusinessException.class,
        () -> service.deleteById(PRICE_ID));
    assertThat(exception, notNullValue());
    assertThat(exception.getErrorCode(), is(ErrorCode.PRICE_NOT_FOUND));
  }

  public static Price buildPrice() {
    return Price.builder()
        .id(1)
        .brand(Brand.builder().id(1).name("ZARA").build())
        .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
        .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
        .priceList(BigDecimal.valueOf(1))
        .productId(35455)
        .priority(0)
        .price(BigDecimal.valueOf(35.50))
        .curr("EUR")
        .build();
  }

}
