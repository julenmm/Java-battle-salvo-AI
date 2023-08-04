package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.request.end.EndGame;
import cs3500.pa04.json.request.end.EndGameResponse;
import cs3500.pa04.model.game.GameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EndGameTest {
  private final ObjectMapper mapper = new ObjectMapper();
  private EndGame endGame1;
  private EndGame endGame2;

  @BeforeEach
  public void setup() {
    endGame1 = new EndGame("EndGame1", new EndGameResponse(GameResult.WIN, "sink all ships"));
    endGame2 =
        new EndGame("EndGame2", new EndGameResponse(GameResult.LOSE, "all your ships are sunk"));
  }

  @Test
  public void testFields() {
    try {
      assertEquals("EndGame1", endGame1.name());
      assertEquals(GameResult.WIN, endGame1.args().result());
      assertEquals("sink all ships", endGame1.args().reason());

      assertEquals("EndGame2", endGame2.name());
      assertEquals(GameResult.LOSE, endGame2.args().result());
      assertEquals("all your ships are sunk", endGame2.args().reason());
    } catch (Exception e) {
      System.out.println("Field verification failed: " + e.getMessage());
    }
  }

  @Test
  public void testSerializationAndDeserialization() {
    try {
      String endGame1Json = mapper.writeValueAsString(endGame1);
      String endGame2Json = mapper.writeValueAsString(endGame2);

      assertEquals(
          "{\"method-name\":\"EndGame1\",\"arguments\":{\"result\":\"WIN\",\"reason\":\"sink all ships\"}}",
          endGame1Json);
      assertEquals(
          "{\"method-name\":\"EndGame2\",\"arguments\":{\"result\":\"LOSE\",\"reason\":\"all your ships are sunk\"}}",
          endGame2Json);

      EndGame deserializedEndGame1 = mapper.readValue(endGame1Json, EndGame.class);
      EndGame deserializedEndGame2 = mapper.readValue(endGame2Json, EndGame.class);

      assertEquals(endGame1, deserializedEndGame1);
      assertEquals(endGame2, deserializedEndGame2);
    } catch (Exception e) {
      System.out.println("Serialization or deserialization failed: " + e.getMessage());
    }
  }
}

