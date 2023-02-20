package se.kry.payments.domain;

import java.time.Instant;
import java.util.UUID;

public record PaymentInfo(UUID id, Instant timestamp, Amount amount) {
}
