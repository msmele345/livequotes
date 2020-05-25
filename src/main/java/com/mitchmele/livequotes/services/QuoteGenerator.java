package com.mitchmele.livequotes.services;

import java.math.BigDecimal;
import java.util.List;

public interface QuoteGenerator {
    String createSymbol();
    List<BigDecimal> createPrice();
}
