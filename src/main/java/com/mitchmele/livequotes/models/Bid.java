package com.mitchmele.livequotes.models;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;


@Data
@Entity
public class Bid {


    @Id
    @GeneratedValue
    int id;

    String type;

    @NonNull
    String symbol;

    @NonNull
    Double bidPrice;

    final String ENTITY_TYPE = "BID";

    public Bid() {}

    public Bid(String symbol, Double bidPrice) {
        this.type = ENTITY_TYPE;
        this.symbol = symbol;
        this.bidPrice = bidPrice;
    }


    public String getSymbol() {
        return symbol;
    }

    public Double getPrice() {
        return bidPrice;
    }
}
