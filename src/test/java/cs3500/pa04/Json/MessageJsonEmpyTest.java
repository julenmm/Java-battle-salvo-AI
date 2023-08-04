package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.EmptyArgs;
import cs3500.pa04.json.MessageJsonEmpy;
import org.junit.jupiter.api.Test;

public class MessageJsonEmpyTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testFields() {
    String methodName = "methodName";
    EmptyArgs arguments = new EmptyArgs();
    MessageJsonEmpy message = new MessageJsonEmpy(methodName, arguments);

    assertEquals(methodName, message.methodName());
    assertEquals(arguments, message.arguments());
  }

  @Test
  public void testSerializationAndDeserialization() throws Exception {
    String methodName = "methodName";
    EmptyArgs arguments = new EmptyArgs();
    MessageJsonEmpy message = new MessageJsonEmpy(methodName, arguments);

    String json = mapper.writeValueAsString(message);
    MessageJsonEmpy deserializedMessage = mapper.readValue(json, MessageJsonEmpy.class);

    assertEquals(methodName, deserializedMessage.methodName());
    assertEquals(arguments, deserializedMessage.arguments());
  }
}

