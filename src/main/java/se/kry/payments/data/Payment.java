package se.kry.payments.data;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Payment extends AbstractPersistable<UUID> {

  @Past
  @NotNull
  private Instant timestamp;

  @NotNull
  @Positive
  private BigDecimal amountValue;

  @NotNull
  private Currency amountCurrency;
}
