package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.response.JoinResponseArguments;
import org.junit.jupiter.api.Test;

public class JsonUtilsTest {
  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testSerializeRecord() {
    JoinResponseArguments args = new JoinResponseArguments("Alice", "SINGLE");
    JsonNode jsonNode = JsonUtils.serializeRecord(args);

    // Validate if the serialization is as expected
    JsonNode expectedJsonNode = mapper.createObjectNode()
        .put("name", "Alice")
        .put("game-type", "SINGLE");
    assertEquals(expectedJsonNode, jsonNode);

    // Test with a non-record type
  }
}

