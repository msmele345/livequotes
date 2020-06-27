package com.mitchmele.livequotes.services;


import com.mitchmele.livequotes.models.Quote;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import static java.util.Arrays.asList;

@Service
public class QuoteProcessor implements QuoteGenerator {

    private final Random ran = new Random();

    public List<Quote> generateQuotes() {
        List<Quote> quotes = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            String symbol = createSymbol();
            List<BigDecimal> price = createPrice();
            Quote newQuote = new Quote(i, symbol, price.get(0), price.get(1), new Date((i * 2) + 1000));
            quotes.add(newQuote);
        }
        return quotes;
    }

    public String createSymbol() {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(chars[ran.nextInt(chars.length)]);
        }
        return stringBuilder.toString();
    }

    public List<BigDecimal> createPrice() {
        double randBidDouble = 0.0 + (100.00 - 0.00) * ran.nextDouble();
        double byPrice = randBidDouble * 0.005;

        double randAskDouble = randBidDouble + byPrice;

        BigDecimal bigDecimalBid = BigDecimal.valueOf(randBidDouble);
        BigDecimal bigDecimalAsk = BigDecimal.valueOf(randAskDouble);

        return asList(
                bigDecimalBid
                        .setScale(2, RoundingMode.HALF_DOWN),
                bigDecimalAsk
                        .setScale(2, RoundingMode.HALF_DOWN)
        );
    }
}
