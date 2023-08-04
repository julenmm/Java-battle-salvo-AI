package cs3500.pa04.Json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.MessageJsonFleet;
import cs3500.pa04.json.objects.CoordJson;
import cs3500.pa04.json.objects.FleetJson;
import cs3500.pa04.json.objects.ShipJson;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MessageJsonFleetTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testFields() {
    String methodName = "methodName";
    FleetJson arguments = new FleetJson(List.of(new ShipJson(new CoordJson(1, 2), 3, "HORIZONTAL")));
    MessageJsonFleet message = new MessageJsonFleet(methodName, arguments);

    assertEquals(methodName, message.methodName());
    assertEquals(arguments, message.arguments());
  }

  @Test
  public void testSerializationAndDeserialization() throws Exception {
    String methodName = "methodName";
    FleetJson arguments = new FleetJson(List.of(new ShipJson(new CoordJson(1, 2), 3, "HORIZONTAL")));
    MessageJsonFleet message = new MessageJsonFleet(methodName, arguments);

    String json = mapper.writeValueAsString(message);
    MessageJsonFleet deserializedMessage = mapper.readValue(json, MessageJsonFleet.class);

    assertEquals(methodName, deserializedMessage.methodName());
    assertEquals(arguments, deserializedMessage.arguments());
  }
}

