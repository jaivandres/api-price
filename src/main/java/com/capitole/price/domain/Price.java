/* (C) Copyright 2024 Frubana. */
package com.frubana.ops.sac.backoffice.customer.domain;

import static com.frubana.ops.sac.backoffice.common.utils.FieldValidator.isParsable;
import static com.frubana.ops.sac.backoffice.common.utils.FieldValidator.nonBlank;

import java.util.Objects;

public class Customer {

  private String id;

  private Customer(Builder builder) {
    setId(builder.id);
    this.validate();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  private void validate() {
    nonBlank(id, "Customer - id");
    isParsable(id, "Customer - id");
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Customer customer)) {
      return false;
    }
    return Objects.equals(getId(), customer.getId());
  }

  public static final class Builder {

    private String id;

    private Builder() {}

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Customer build() {
      return new Customer(this);
    }
  }

}
