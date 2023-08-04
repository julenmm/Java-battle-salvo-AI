package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.response.JoinResponseArguments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JoinResponseArgumentsTest {
  private final ObjectMapper mapper = new ObjectMapper();
  private JoinResponseArguments args;

  @BeforeEach
  public void setup() {
    args = new JoinResponseArguments("Alice", "SINGLE");
  }

  @Test
  public void testFields() {
    try {
      assertEquals("Alice", args.name());
      assertEquals("SINGLE", args.gameType());
    } catch (Exception e) {
      System.out.println("Field verification failed: " + e.getMessage());
    }
  }

  @Test
  public void testSerializationAndDeserialization() {
    try {
      String argsJson = mapper.writeValueAsString(args);
      JoinResponseArguments deserializedArgs =
          mapper.readValue(argsJson, JoinResponseArguments.class);
      assertEquals(args, deserializedArgs);
    } catch (Exception e) {
      System.out.println("Serialization or deserialization failed: " + e.getMessage());
    }
  }
}

