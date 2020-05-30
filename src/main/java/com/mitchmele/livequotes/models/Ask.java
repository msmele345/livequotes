package com.mitchmele.livequotes.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "ASK")
public class Ask implements QuotePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Integer id;

    String symbol;

    @Column(name = "Askprice")
    private BigDecimal askPrice;

    public Ask() {
    }

    public Ask(Integer id, String symbol, BigDecimal askPrice) {
        this.id = id;
        this.symbol = symbol;
        this.askPrice = askPrice;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return askPrice;
    }
}
