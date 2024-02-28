/* (C) Copyright 2024 Capitole. */
package com.capitole.price.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capitole.price.domain.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

  @Query("SELECT p FROM Price p " +
      "JOIN FETCH p.brand b " +
      "WHERE :applicationDate BETWEEN p.startDate AND p.endDate " +
      "AND p.productId = :productId " +
      "AND b.id = :brandId")
  List<Price> find(
      @Param("applicationDate") LocalDateTime applicationDate,
      @Param("productId") Integer productId,
      @Param("brandId") String brandId);

}
