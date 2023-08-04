package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.MessageJsonVolley;
import cs3500.pa04.json.objects.CoordJson;
import cs3500.pa04.json.objects.VolleyJson;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MessageJsonVolleyTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testFields() {
    String methodName = "methodName";
    VolleyJson args = new VolleyJson(List.of(new CoordJson(1, 2)));
    MessageJsonVolley message = new MessageJsonVolley(methodName, args);

    assertEquals(methodName, message.name());
    assertEquals(args, message.args());
  }

  @Test
  public void testSerializationAndDeserialization() throws Exception {
    String methodName = "methodName";
    VolleyJson args = new VolleyJson(List.of(new CoordJson(1, 2)));
    MessageJsonVolley message = new MessageJsonVolley(methodName, args);

    String json = mapper.writeValueAsString(message);
    MessageJsonVolley deserializedMessage = mapper.readValue(json, MessageJsonVolley.class);

    assertEquals(methodName, deserializedMessage.name());
    assertEquals(args, deserializedMessage.args());
  }
}

