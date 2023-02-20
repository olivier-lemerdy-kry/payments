package se.kry.payments;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentsApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void actuator_health_up() throws Exception {
    mockMvc.perform(get("/actuator/health"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value("UP"));
  }

  @Test
  void scenario() throws Exception {
    String payload = """
        {"value": "10", "currency": "EUR"}
        """;

    mockMvc.perform(post("/api/v1/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNotEmpty());

    mockMvc.perform(get("/api/v1/payments"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].amount.value").value(10))
        .andExpect(jsonPath("$[0].amount.currency").value("EUR"));
  }

}
