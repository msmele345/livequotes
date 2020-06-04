package com.mitchmele.livequotes.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "QUOTE")
public class Quote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    private String symbol;

    @Column(name = "Bidprice")
    private BigDecimal bidPrice;

    @Column(name = "Askprice")
    private BigDecimal askPrice;


    @Column(name = "CREATED_TS", updatable = false)
    @CreationTimestamp
    private Date timeStamp;

}
