package se.kry.payments.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Currency;

public record Amount(@Positive BigDecimal value, @NotNull Currency currency) {
}
