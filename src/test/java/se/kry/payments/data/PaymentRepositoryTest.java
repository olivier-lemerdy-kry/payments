package se.kry.payments.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.kry.payments.domain.AmountFixtures;

@DataJpaTest
class PaymentRepositoryTest {

  @Autowired
  private PaymentRepository repository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  void save_OK() {
    var toBeSaved = new Payment(Instant.EPOCH, AmountFixtures.VALUE, AmountFixtures.CURRENCY);
    assertThat(toBeSaved.getId()).isNull();

    var saved = repository.save(toBeSaved);
    assertThat(saved.getId()).isNotNull();

    entityManager.flush();
  }

  @Test
  void save_negative_amount_FAILS() {
    var toBeSaved = new Payment(Instant.EPOCH, BigDecimal.TEN.negate(), AmountFixtures.CURRENCY);
    assertThat(toBeSaved.getId()).isNull();

    var saved = repository.save(toBeSaved);

    assertThatThrownBy(() -> entityManager.flush())
        .isInstanceOfSatisfying(ConstraintViolationException.class, exception -> {
          assertThat(exception.getConstraintViolations()).hasSize(1);
        });
  }

}