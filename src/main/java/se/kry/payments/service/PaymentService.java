package se.kry.payments.service;

import java.time.Clock;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import se.kry.payments.data.Payment;
import se.kry.payments.data.PaymentRepository;
import se.kry.payments.domain.Amount;
import se.kry.payments.domain.PaymentInfo;

@Service
public class PaymentService {

  private final Clock clock;

  private final PaymentRepository repository;

  public PaymentService(Clock clock, PaymentRepository repository) {
    this.clock = clock;
    this.repository = repository;
  }

  public List<PaymentInfo> getAllPayments() {
    return repository.findAll().stream()
        .map(PaymentService::getPaymentInfo)
        .toList();
  }

  public Optional<PaymentInfo> getPayment(UUID id) {
    return repository.findById(id).map(PaymentService::getPaymentInfo);
  }

  public PaymentInfo createPayment(Amount amount) {
    var toBeSaved = new Payment(clock.instant(), amount.value(), amount.currency());
    var saved = repository.save(toBeSaved);

    return getPaymentInfo(saved);
  }

  private static PaymentInfo getPaymentInfo(Payment payment) {
    return new PaymentInfo(payment.getId(), payment.getTimestamp(),
        new Amount(payment.getAmountValue(), payment.getAmountCurrency()));
  }
}
