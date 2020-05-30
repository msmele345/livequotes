package com.mitchmele.livequotes.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Random;

@Data
@Entity
//@Table(name = "quote", schema = "dbo")
@Table(name = "quote_tbl")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Integer id;

    @NonNull
    String symbol;

    @Column(name = "Bidprice")
    BigDecimal bidPrice;

    @Column(name = "Askprice")
    BigDecimal askPrice;

    public Quote() {}

    public Quote(@NonNull String symbol, BigDecimal bidPrice, BigDecimal askPrice) {
        this.symbol = symbol;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    public Integer getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public BigDecimal getAskPrice() {
        return askPrice;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", bidPrice=" + bidPrice +
                ", askPrice=" + askPrice +
                '}';
    }
}
