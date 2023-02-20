package se.kry.payments.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class AmountJsonTest {

  @Autowired
  private JacksonTester<Amount> jacksonTester;

  @Test
  void serialize() throws IOException {
    var jsonContent = jacksonTester.write(AmountFixtures.AMOUNT);

    assertThat(jsonContent).isEqualToJson("Amount.json");
  }

  @Test
  void deserialize() throws IOException {
    var amount = jacksonTester.readObject("Amount.json");

    assertThat(amount.value()).isEqualTo(AmountFixtures.VALUE);
    assertThat(amount.currency()).isEqualTo(AmountFixtures.CURRENCY);
  }

}