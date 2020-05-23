package com.mitchmele.livequotes.sqlserver;

import com.mitchmele.livequotes.models.Quote;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Integer> {
    List<Quote> findAllBySymbol(String symbol);
}
