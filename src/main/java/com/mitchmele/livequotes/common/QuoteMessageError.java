package com.mitchmele.livequotes.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class QuoteMessageError {

    Throwable cause;
    String domain;
    QuoteErrorType quoteErrorType;
    String exMessage;
}
