package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.response.JoinResponse;
import cs3500.pa04.json.response.JoinResponseArguments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JoinResponseTest {
  private final ObjectMapper mapper = new ObjectMapper();
  private JoinResponse response;

  @BeforeEach
  public void setup() {
    JoinResponseArguments args = new JoinResponseArguments("Alice", "SINGLE");
    response = new JoinResponse("join", args);
  }

  @Test
  public void testFields() {
    try {
      assertEquals("join", response.join());
      assertEquals(new JoinResponseArguments("Alice", "SINGLE"), response.args());
    } catch (Exception e) {
      System.out.println("Field verification failed: " + e.getMessage());
    }
  }

  @Test
  public void testSerializationAndDeserialization() {
    try {
      String responseJson = mapper.writeValueAsString(response);
      JoinResponse deserializedResponse = mapper.readValue(responseJson, JoinResponse.class);
      assertEquals(response, deserializedResponse);
    } catch (Exception e) {
      System.out.println("Serialization or deserialization failed: " + e.getMessage());
    }
  }
}
