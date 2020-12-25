package com.mitchmele.livequotes.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Ask")
public class AskDO implements QuotePrice, Serializable {

    @Id
    private Integer id;

    private String symbol;

    private BigDecimal askPrice;

    @CreationTimestamp
    private Date timeStamp;

    @Override
    public BigDecimal getPrice() {
        return askPrice;
    }
}
