package se.kry.payments.domain;

import java.math.BigDecimal;
import java.util.Currency;

public interface AmountFixtures {

  Currency CURRENCY = Currency.getInstance("EUR");

  BigDecimal VALUE = BigDecimal.TEN;

  Amount AMOUNT = new Amount(VALUE, CURRENCY);
}
