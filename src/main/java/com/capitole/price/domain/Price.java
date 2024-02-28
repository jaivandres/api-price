/* (C) Copyright 2024 Capitole. */
package com.capitole.price.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "prices")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Price {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "brand_id")
  private String brandId;

  @Column(name = "start_date")
  private LocalDateTime startDate;

  @Column(name = "end_date")
  private LocalDateTime endDate;

  @Column(name = "price_list")
  private BigDecimal priceList;

  @Column(name = "product_id")
  private Integer productId;

  @Column(name = "priority")
  private Integer priority;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "curr")
  private String curr;

}
