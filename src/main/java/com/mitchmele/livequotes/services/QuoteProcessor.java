package com.mitchmele.livequotes.services;


import com.mitchmele.livequotes.models.Bid;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuoteProcessor implements QuoteGenerator {

    private final Random ran = new Random();

    public List<Bid> generateBids() {
        List<Bid> bids = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            String symbol = createSymbol();
            Double price = createPrice();
            Bid newBid = new Bid(symbol, price);
            bids.add(newBid);
        }
        return bids;
    }


    public String createSymbol() {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(chars[ran.nextInt(chars.length)]);
        }
        return stringBuilder.toString();
    }

    public Double createPrice() {
        double randDouble = 0.0 + (100.00 - 0.00) * ran.nextDouble();
        DecimalFormat df = new DecimalFormat("#.##");
        randDouble = Double.parseDouble(df.format(randDouble));
        return randDouble;
    }
}
