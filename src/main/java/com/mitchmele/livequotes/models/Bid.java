package com.mitchmele.livequotes.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "BID")
public class Bid implements QuotePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Integer id;

    String symbol;

    @Column(name = "Bidprice")
    BigDecimal bidPrice;

    public Bid() {}

    public Bid(Integer id, String symbol, BigDecimal bidPrice) {
        this.id = id;
        this.symbol = symbol;
        this.bidPrice = bidPrice;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return bidPrice;
    }
}
