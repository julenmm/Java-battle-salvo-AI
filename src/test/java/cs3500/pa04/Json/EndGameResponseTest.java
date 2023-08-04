package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.request.end.EndGameResponse;
import cs3500.pa04.model.game.GameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EndGameResponseTest {
  private final ObjectMapper mapper = new ObjectMapper();
  private EndGameResponse response1;
  private EndGameResponse response2;

  @BeforeEach
  public void setup() {
    response1 = new EndGameResponse(GameResult.WIN, "all opponent ships are sunk");
    response2 = new EndGameResponse(GameResult.LOSE, "all your ships are sunk");
  }

  @Test
  public void testFields() {
    try {
      assertEquals(GameResult.WIN, response1.result());
      assertEquals("all opponent ships are sunk", response1.reason());

      assertEquals(GameResult.LOSE, response2.result());
      assertEquals("all your ships are sunk", response2.reason());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testSerializationAndDeserialization() {
    try {
      String response1Json = mapper.writeValueAsString(response1);
      String response2Json = mapper.writeValueAsString(response2);

      assertEquals("{\"result\":\"WIN\",\"reason\":\"all opponent ships are sunk\"}",
          response1Json);
      assertEquals("{\"result\":\"LOSE\",\"reason\":\"all your ships are sunk\"}", response2Json);

      EndGameResponse deserializedResponse1 =
          mapper.readValue(response1Json, EndGameResponse.class);
      EndGameResponse deserializedResponse2 =
          mapper.readValue(response2Json, EndGameResponse.class);

      assertEquals(response1, deserializedResponse1);
      assertEquals(response2, deserializedResponse2);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
