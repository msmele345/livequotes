package com.mitchmele.livequotes.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Data
//@Entity
public class Ask  {

    @Id
    @GeneratedValue
    int id;

    String type;

    @NonNull
    String symbol;

    @NonNull
    Double askPrice;

    final String ENTITY_TYPE = "ASK";

    public Ask() { }

    public Ask(String symbol, Double askPrice) {
        this.type = ENTITY_TYPE;
        this.symbol = symbol;
        this.askPrice = askPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getPrice() {
        return askPrice;
    }
}
