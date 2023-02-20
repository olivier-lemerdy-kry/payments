package se.kry.payments.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.kry.payments.domain.Amount;
import se.kry.payments.domain.PaymentInfo;
import se.kry.payments.service.PaymentService;

@WebMvcTest
class PaymentsControllerTest {

  private static final Currency EUR = Currency.getInstance("EUR");

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PaymentService paymentService;

  @Test
  void get_all_payments_OK() throws Exception {
    var uuid = UUID.fromString("69f655d1-05b9-4220-9e5f-11762ef77e1c");
    var paymentInfo = new PaymentInfo(uuid, Instant.EPOCH, new Amount(BigDecimal.TEN, EUR));
    when(paymentService.getAllPayments())
        .thenReturn(List.of(paymentInfo));

    mockMvc.perform(get("/api/v1/payments"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value("69f655d1-05b9-4220-9e5f-11762ef77e1c"))
        .andExpect(jsonPath("$[0].timestamp").value("1970-01-01T00:00:00Z"))
        .andExpect(jsonPath("$[0].amount.value").value(10))
        .andExpect(jsonPath("$[0].amount.currency").value("EUR"));
  }

  @Test
  void get_payment_OK() throws Exception {
    var uuid = UUID.fromString("69f655d1-05b9-4220-9e5f-11762ef77e1c");
    var paymentInfo = new PaymentInfo(uuid, Instant.EPOCH, new Amount(BigDecimal.TEN, EUR));
    when(paymentService.getPayment(uuid))
        .thenReturn(paymentInfo);

    mockMvc.perform(get("/api/v1/payments/69f655d1-05b9-4220-9e5f-11762ef77e1c"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("69f655d1-05b9-4220-9e5f-11762ef77e1c"))
        .andExpect(jsonPath("$.timestamp").value("1970-01-01T00:00:00Z"))
        .andExpect(jsonPath("$.amount.value").value(10))
        .andExpect(jsonPath("$.amount.currency").value("EUR"));
  }

  @Test
  void post_payment_OK() throws Exception {
    var uuid = UUID.fromString("69f655d1-05b9-4220-9e5f-11762ef77e1c");
    var paymentInfo = new PaymentInfo(uuid, Instant.EPOCH, new Amount(BigDecimal.TEN, EUR));
    when(paymentService.createPayment(new Amount(BigDecimal.TEN, EUR)))
        .thenReturn(paymentInfo);

    String payload = """
        {"value": "10", "currency": "EUR"}
        """;

    mockMvc.perform(post("/api/v1/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value("69f655d1-05b9-4220-9e5f-11762ef77e1c"))
        .andExpect(jsonPath("$.timestamp").value("1970-01-01T00:00:00Z"))
        .andExpect(jsonPath("$.amount.value").value(10))
        .andExpect(jsonPath("$.amount.currency").value("EUR"));
  }

  @Test
  void post_payment_negative_amount_FAILS() throws Exception {
    String payload = """
        {"value": "-10", "currency": "EUR"}
        """;

    mockMvc.perform(post("/api/v1/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isBadRequest());
  }

  @Test
  void post_payment_no_current_FAILS() throws Exception {
    String payload = """
        {"value": "10"}
        """;

    mockMvc.perform(post("/api/v1/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isBadRequest());
  }
}
