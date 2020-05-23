package com.mitchmele.livequotes.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Random;

@Data
@Entity
@Table(name = "quote", schema = "dbo")
public class Quote {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Integer id;

    @NonNull
    String symbol;

    @Column(name = "Bidprice")
    Double bidPrice;

    @Column(name = "Askprice")
    Double askPrice;

    public Quote() {}

    public Quote(@NonNull String symbol, Double bidPrice, double askPrice) {
        this.id = setId();
        this.symbol = symbol;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    public Integer setId() {
        Random random = new Random();
        return random.nextInt();
    }

    public Integer getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public Double getAskPrice() {
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
