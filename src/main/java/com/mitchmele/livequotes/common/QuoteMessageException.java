package com.mitchmele.livequotes.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuoteMessageException extends RuntimeException {

     String message;
     List<QuoteMessageError> errors = new ArrayList<>();

     public QuoteMessageException(QuoteMessageError...error) {
         this.errors.addAll(Arrays.stream(error).collect(Collectors.toList()));
         this.message = errors.stream()
                 .map(e -> "Error Type: " + e.quoteErrorType + ", " + "Domain: " + e.domain + ", Message: " + e.exMessage)
                 .collect(Collectors.joining());
     }


     public QuoteMessageException(String message) {
         this.message = message;
     }

    @Override
    public String getMessage() {
        return message;
    }
}
//message and array list of custom errors
//first constructor just takes a message
//second constructor takes varargs of errors
    //-stream varargs into list and add all to instance variable errors
    //set instance variable message to a joined string of all vararg errors with mapped message