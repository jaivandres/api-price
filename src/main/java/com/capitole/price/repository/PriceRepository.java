/* (C) Copyright 2024 Frubana. */
package com.frubana.ops.sac.backoffice.customer.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import org.springframework.stereotype.Repository;

import com.frubana.ops.sac.backoffice.common.utils.AbstractRepository;
import com.frubana.ops.sac.backoffice.customer.domain.Customer;


@Repository
public class CustomerRepository extends AbstractRepository<Customer> {

  private static final String TABLE_NAME = "customers";
  private static final String INSERT_QUERY_STRING =
      "INSERT INTO %s (id) VALUES (:id)".formatted(TABLE_NAME);
  private static final String SELECT_QUERY_STRING = "SELECT * FROM %s".formatted(TABLE_NAME);
  private static final String SELECT_BY_ID_QUERY_STRING =
      "%s WHERE id = :id".formatted(SELECT_QUERY_STRING);
  private static final String DELETE_QUERY_STRING =
      "DELETE FROM %s WHERE id = :id".formatted(TABLE_NAME);

  protected CustomerRepository(Jdbi jdbi) {
    super(jdbi, CustomerRepository::map);
  }

  private static Update getInsertQuery(Customer customer, Handle handle) {
    return handle.createUpdate(INSERT_QUERY_STRING)
        .bind("id", customer.getId());
  }

  private static Update getDeleteQuery(String id, Handle handle) {
    return handle.createUpdate(DELETE_QUERY_STRING)
        .bind("id", id);
  }

  private static Customer map(ResultSet rs) throws SQLException {
    return Customer.newBuilder()
        .withId(rs.getString("id"))
        .build();
  }

  private Query getSelectById(String id, Handle handle) {
    return handle.createQuery(SELECT_BY_ID_QUERY_STRING).bind("id", id);
  }

  public Customer create(Customer customer) {
    this.createUpdate(handle -> getInsertQuery(customer, handle));
    return customer;
  }

  public Optional<Customer> findById(String id) {
    return this.findOne(handle -> getSelectById(id, handle));
  }

  public void delete(String id) {
    this.createUpdate(handle -> getDeleteQuery(id, handle));
  }

}
