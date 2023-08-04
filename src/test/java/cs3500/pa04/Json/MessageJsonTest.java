package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.MessageJson;
import org.junit.jupiter.api.Test;

public class MessageJsonTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testFields() {
    String methodName = "methodName";
    JsonNode args = mapper.createObjectNode();
    MessageJson message = new MessageJson(methodName, args);

    assertEquals(methodName, message.name());
    assertEquals(args, message.args());
  }

  @Test
  public void testSerializationAndDeserialization() throws Exception {
    String methodName = "methodName";
    JsonNode args = mapper.createObjectNode();
    MessageJson message = new MessageJson(methodName, args);

    String json = mapper.writeValueAsString(message);
    MessageJson deserializedMessage = mapper.readValue(json, MessageJson.class);

    assertEquals(methodName, deserializedMessage.name());
    assertEquals(args, deserializedMessage.args());
  }
}

