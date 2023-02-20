package se.kry.payments.web;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.kry.payments.domain.Amount;
import se.kry.payments.domain.PaymentInfo;
import se.kry.payments.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentsController {

  private final PaymentService paymentService;

  public PaymentsController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping
  List<PaymentInfo> getAllPayments() {
    return paymentService.getAllPayments();
  }

  @GetMapping("{id}")
  ResponseEntity<PaymentInfo> getPayment(@PathVariable UUID id) {
    return paymentService.getPayment(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  PaymentInfo createPayment(@RequestBody @Valid Amount amount) {
    return paymentService.createPayment(amount);
  }

}
