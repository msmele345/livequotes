package com.mitchmele.livequotes.models;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;

public interface QuotePrice {
    Integer getId();
    String getSymbol();
    BigDecimal getPrice();
}
